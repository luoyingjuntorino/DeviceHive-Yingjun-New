package com.devicehive.resource;

/*
 * #%L
 * DeviceHive Java Server Common business logic
 * %%
 * Copyright (C) 2016 DataArt
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.devicehive.model.response.EntityCountResponse;
import com.devicehive.model.updates.IcomponentUpdate;
import com.devicehive.vo.IcomponentVO;
import com.devicehive.vo.IcomponentWithUsersAndDevicesVO;
import io.swagger.annotations.*;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Response;

@Api(tags = {"Icomponent"}, value = "Represents a icomponent, an isolated area where devices reside.", consumes="application/json")
@Path("/icomponent")
public interface IcomponentResource {

    /**
     * Produces following output:
     * <pre>
     * [
     *  {
     *    "description":"Icomponent Description",
     *    "id":1,
     *    "name":"Icomponent Name"
     *   },
     *   {
     *    "description":"Icomponent Description",
     *    "id":2,
     *    "name":"Icomponent Name"
     *   }
     * ]
     * </pre>
     *
     * @param name        exact icomponent's name, ignored, when  namePattern is not null
     * @param namePattern name pattern
     * @param sortField   Sort Field, can be either "id", "key", "name" or "description"
     * @param sortOrderSt ASC - ascending, otherwise descending
     * @param take        limit, default 1000
     * @param skip        offset, default 0
     */
    @GET
    @PreAuthorize("isAuthenticated() and hasPermission(null, 'GET_ICOMPONENT')")
    @ApiOperation(value = "List icomponents", notes = "Gets list of icomponents the client has access to.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "If successful, this method returns array of Icomponent resources in the response body.", response = IcomponentVO.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "If request parameters invalid"),
            @ApiResponse(code = 401, message = "If request is not authorized"),
            @ApiResponse(code = 403, message = "If principal doesn't have permissions")
    })
    void list(
            @ApiParam(name = "name", value = "Filter by icomponent name.")
            @QueryParam("name")
                    String name,
            @ApiParam(name = "namePattern", value = "Filter by icomponent name pattern. In pattern wildcards '%' and '_' can be used.")
            @QueryParam("namePattern")
                    String namePattern,
            @ApiParam(name = "sortField", value = "Result list sort field.", allowableValues = "ID,Name")
            @QueryParam("sortField")
                    String sortField,
            @ApiParam(name = "sortOrder", value = "Result list sort order. The sortField should be specified.", allowableValues = "ASC,DESC")
            @QueryParam("sortOrder")
                    String sortOrderSt,
            @ApiParam(name = "take", value = "Number of records to take from the result list.", defaultValue = "20")
            @QueryParam("take")
            @Min(0) @Max(Integer.MAX_VALUE)
                    Integer take,
            @ApiParam(name = "skip", value = "Number of records to skip from the result list.", defaultValue = "0")
            @QueryParam("skip")
                    Integer skip,
            @Suspended final AsyncResponse asyncResponse
    );

    /**
     * Generates JSON similar to this:
     * <pre>
     *     {
     *         "count":1
     *     }
     * </pre>
     *
     * @param name        exact icomponent's name, ignored, when namePattern is not null
     * @param namePattern name pattern
     */
    @GET
    @Path("/count")
    @PreAuthorize("isAuthenticated() and hasPermission(null, 'GET_ICOMPONENT')")
    @ApiOperation(value = "Count icomponents", notes = "Gets count of icomponents the client has access to.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "If successful, this method returns the count of Icomponent resources in the response body.",
                    response = EntityCountResponse.class, responseContainer = "Count"),
            @ApiResponse(code = 400, message = "If request parameters invalid"),
            @ApiResponse(code = 401, message = "If request is not authorized"),
            @ApiResponse(code = 403, message = "If principal doesn't have permissions")
    })
    void count(
            @ApiParam(name = "name", value = "Filter by icomponent name.")
            @QueryParam("name")
                    String name,
            @ApiParam(name = "namePattern", value = "Filter by icomponent name pattern. In pattern wildcards '%' and '_' can be used.")
            @QueryParam("namePattern")
                    String namePattern,
            @Suspended final AsyncResponse asyncResponse
    );

    /**
     * Generates  JSON similar to this:
     * <pre>
     *     {
     *      "description":"Icomponent Description",
     *      "id":1,
     *      "name":"Icomponent Name"
     *     }
     * </pre>
     *
     * @param id icomponent id, can't be null
     */
    @GET
    @Path("/{id}")
    @PreAuthorize("isAuthenticated() and hasPermission(#id, 'GET_ICOMPONENT')")
    @ApiOperation(value = "Get icomponent", notes = "Gets information about icomponent and its devices.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "If successful, this method returns a Icomponent resource in the response body.", response = IcomponentWithUsersAndDevicesVO.class),
            @ApiResponse(code = 400, message = "If request is malformed"),
            @ApiResponse(code = 401, message = "If request is not authorized"),
            @ApiResponse(code = 403, message = "If principal doesn't have permissions"),
            @ApiResponse(code = 404, message = "If icomponent not found")
    })
    Response get(
            @ApiParam(name = "id", value = "Icomponent identifier.")
            @PathParam("id")
                    long id);

    /**
     * Inserts new Icomponent into database. Consumes next input:
     * <pre>
     *     {
     *       "name":"Icomponent Name",
     *       "description":"Icomponent Description"
     *     }
     * </pre>
     * Where is not required "name" is required <p/> In case of success will produce
     * following output:
     * <pre>
     *     {
     *      "description":"Icomponent Description",
     *      "id":1,
     *      "name":"Icomponent Name"
     *     }
     * </pre>
     * Where "description"will be provided, if they are specified in request. Fields "id" and "name" will be
     * provided anyway.
     */
    @POST
    @PreAuthorize("isAuthenticated() and hasPermission(null, 'MANAGE_ICOMPONENT')")
    @ApiOperation(value = "Create icomponent", notes = "Creates new icomponent.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header")
    })
    @ApiResponses({
            @ApiResponse(code = 201, message = "If successful, this method returns a Icomponent resource in the response body.", response = IcomponentVO.class),
            @ApiResponse(code = 400, message = "If request is malformed"),
            @ApiResponse(code = 401, message = "If request is not authorized"),
            @ApiResponse(code = 403, message = "If principal doesn't have permissions")
    })
    Response insert(
            @ApiParam(value = "Icomponent body", defaultValue = "{}", required = true)
            IcomponentUpdate icomponent);

    /**
     * This method updates icomponent with given Id. Consumes following input:
     * <pre>
     *     {
     *       "name":"Icomponent Name",
     *       "description":"Icomponent Description"
     *     }
     * </pre>
     * Where "description" is not required "name" is not required Fields, that are not specified
     * will stay unchanged Method will produce following output:
     * <pre>
     *     {
     *      "description":"Icomponent Description",
     *      "id":1,
     *      "name":"Icomponent Name"
     *     }
     * </pre>
     */
    @PUT
    @Path("/{id}")
    @PreAuthorize("isAuthenticated() and hasPermission(#id, 'MANAGE_ICOMPONENT')")
    @ApiOperation(value = "Update icomponent", notes = "Updates an existing icomponent.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header")
    })
    @ApiResponses({
            @ApiResponse(code = 204, message = "If successful, this method returns an empty response body."),
            @ApiResponse(code = 400, message = "If request is malformed"),
            @ApiResponse(code = 401, message = "If request is not authorized"),
            @ApiResponse(code = 403, message = "If principal doesn't have permissions")
    })
    Response update(
            @ApiParam(value = "Icomponent body", defaultValue = "{}", required = true)
            IcomponentUpdate icomponentToUpdate,
            @ApiParam(name = "id", value = "Icomponent identifier.", required = true)
            @PathParam("id")
                    long id);

    /**
     * Deletes icomponent by specified id. If success, outputs empty response
     *
     * @param id icomponent's id
     */
    @DELETE
    @Path("/{id}")
    @PreAuthorize("isAuthenticated() and hasPermission(#id, 'MANAGE_ICOMPONENT')")
    @ApiOperation(value = "Delete icomponent", notes = "Deletes an existing icomponent.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header")
    })
    @ApiResponses({
            @ApiResponse(code = 204, message = "If successful, this method returns an empty response body."),
            @ApiResponse(code = 401, message = "If request is not authorized"),
            @ApiResponse(code = 403, message = "If principal doesn't have permissions"),
            @ApiResponse(code = 404, message = "If icomponent not found")
    })
    Response delete(
            @ApiParam(name = "id", value = "Icomponent identifier.", required = true)
            @PathParam("id")
                    long id,
            @ApiParam(name = "force", value = "Force deletion flag.", defaultValue = "false")
            @QueryParam("force")
                    boolean force);

}
