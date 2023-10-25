package com.devicehive.model.rpc;

/*
 * #%L
 * DeviceHive Backend Logic
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

import com.devicehive.auth.HivePrincipal;
import com.devicehive.shim.api.Action;
import com.devicehive.shim.api.Body;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.lang.reflect.Modifier;
import java.util.Optional;

public class CountIexperimentRequest extends Body {

    private String name;
    private String namePattern;
    private Optional<HivePrincipal> principal;

    public CountIexperimentRequest() {
        super(Action.COUNT_IEXPERIMENT_REQUEST);
    }

    public CountIexperimentRequest(String name, String namePattern, Optional<HivePrincipal> principal) {
        super(Action.COUNT_IEXPERIMENT_REQUEST);
        this.name = name;
        this.namePattern = namePattern;
        this.principal = principal;
    }

    public static CountIexperimentRequest createCountIexperimentRequest(JsonObject request) {
        CountIexperimentRequest countIexperimentRequest = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.PROTECTED)
                .create()
                .fromJson(request, CountIexperimentRequest.class);

        return countIexperimentRequest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamePattern() {
        return namePattern;
    }

    public void setNamePattern(String namePattern) {
        this.namePattern = namePattern;
    }

    public Optional<HivePrincipal> getPrincipal() {
        return principal;
    }

    public void setPrincipal(Optional<HivePrincipal> principal) {
        this.principal = principal;
    }
}
