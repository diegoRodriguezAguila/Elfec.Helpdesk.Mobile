package com.elfec.helpdesk.web_service.api_endpoints;

import android.support.annotation.NonNull;

import com.elfec.helpdesk.model.Requirement;
import com.elfec.helpdesk.model.RequirementApproval;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * API endpoint for /requirements
 */
public interface IRequirementsEndpoint {

    @NonNull
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })

    @PUT("requirements/{id}")
    Call<Requirement> updateApprovalStatus(@Path("id") String requirementId, @Body RequirementApproval
            requirementApproval);
}
