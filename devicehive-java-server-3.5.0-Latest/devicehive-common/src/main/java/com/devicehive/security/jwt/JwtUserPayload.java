package com.devicehive.security.jwt;

/*
 * #%L
 * DeviceHive Common Module
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

/**
 * Common JWT entity
 * Structure must be as provided below:
 * {
 *     "userId": user_id,
 *     "actions": ["action1","action2","actionN"],
 *     "networkIds": ["id1","id2","idN"],
 *     "deviceIds": ["id1","id2","idN"]
 * }
 *
 * To get admin permissions (to all actions, networks, etc) you have to specify "*" for string parameters:
 * {
 *     "userId": user_id,
 *     "actions": ["*"],
 *     "networkIds": ["*"],
 *     "deviceIds": ["*"]
 * }
 */
public class JwtUserPayload extends JwtPayload {

    private static final long serialVersionUID = -6904689203121394308L;

    public final static String USER_ID = "u";
    public final static String NETWORK_IDS = "n";
    public final static String IEXPERIMENT_IDS = "dt";
    public final static String ICOMPONENT_IDS = "cp";
    
    //Public claims

    @NotNull
    @SerializedName(USER_ID)
    @JsonProperty(USER_ID)
    private Long userId;

    @SerializedName(NETWORK_IDS)
    @JsonProperty(NETWORK_IDS)
    private Set<String> networkIds;

    @SerializedName(IEXPERIMENT_IDS)
    @JsonProperty(IEXPERIMENT_IDS)
    private Set<String> iexperimentIds;

    @SerializedName(ICOMPONENT_IDS)
    @JsonProperty(ICOMPONENT_IDS)
    private Set<String> icomponentIds;

    public JwtUserPayload(Long userId, Set<Integer> actions, Set<String> networkIds,
                       Set<String> iexperimentIds, Set<String> icomponentIds, Date expiration, Integer tokenType) {
        super(actions, expiration, tokenType);
        this.userId = userId;
        this.networkIds = networkIds;
        this.iexperimentIds = iexperimentIds;
        this.icomponentIds = icomponentIds;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<String> getNetworkIds() {
        return networkIds;
    }

    public void setNetworkIds(Set<String> networkIds) {
        this.networkIds = networkIds;
    }

    public Set<String> getIexperimentIds() {
        return iexperimentIds;
    }

    public void setIexperimentIds(Set<String> iexperimentIds) {
        this.iexperimentIds = iexperimentIds;
    }

    public Set<String> getIcomponentIds() {
        return icomponentIds;
    }

    public void setIcomponentIds(Set<String> icomponentIds) {
        this.icomponentIds = icomponentIds;
    }

    @Override
    @JsonIgnore
    public boolean isUserPayload() {
        return true;
    }

    public static JwtUserPayloadBuilder newBuilder() {
        return new JwtUserPayloadBuilder();
    }

    public static class JwtUserPayloadBuilder extends JwtPayloadBuilder {
        private Long userId;
        private Set<String> networkIds;
        private Set<String> iexperimentIds;
        private Set<String> icomponentIds;
        
        public JwtUserPayloadBuilder withPublicClaims(Long userId, Set<Integer> actions,
                                        Set<String> networkIds, Set<String> iexperimentIds,
                                        Set<String> icomponentIds) {
            this.userId = userId;
            this.actions = actions;
            this.networkIds = networkIds;
            this.iexperimentIds = iexperimentIds;
            this.icomponentIds = icomponentIds;
            return this;
        }

        public JwtUserPayloadBuilder withPayload(JwtUserPayload payload) {
            this.userId = payload.getUserId();
            this.actions = payload.getActions();
            this.networkIds = payload.getNetworkIds();
            this.iexperimentIds = payload.getIexperimentIds();
            this.icomponentIds = payload.getIcomponentIds();
            this.expiration = payload.getExpiration();
            return this;
        }

        public JwtUserPayloadBuilder withUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public JwtUserPayloadBuilder withActions(Set<Integer> actions) {
            this.actions = actions;
            return this;
        }

        public JwtUserPayloadBuilder withNetworkIds(Set<String> networkIds) {
            this.networkIds = networkIds;
            return this;
        }

        public JwtUserPayloadBuilder withIexperimentIds(Set<String> iexperimentIds) {
            this.iexperimentIds = iexperimentIds;
            return this;
        }

        public JwtUserPayloadBuilder withIcomponentIds(Set<String> icomponentIds) {
            this.icomponentIds = icomponentIds;
            return this;
        }

        public JwtUserPayloadBuilder withTokenType(Integer tokenType) {
            this.tokenType = tokenType;
            return this;
        }

        public JwtUserPayloadBuilder withExpirationDate(Date expiration) {
            this.expiration = expiration;
            return this;
        }

        public JwtUserPayload buildPayload() {
            return new JwtUserPayload(userId, actions, networkIds, iexperimentIds, icomponentIds, expiration, tokenType);
        }
    }
}
