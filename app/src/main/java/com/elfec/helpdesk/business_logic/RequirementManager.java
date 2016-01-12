package com.elfec.helpdesk.business_logic;

import com.elfec.helpdesk.model.Requirement;
import com.elfec.helpdesk.model.RequirementApproval;
import com.elfec.helpdesk.security.AppPreferences;
import com.elfec.helpdesk.security.CredentialManager;
import com.elfec.helpdesk.web_service.RestEndpointFactory;
import com.elfec.helpdesk.web_service.api_endpoints.IRequirementsEndpoint;

import retrofit2.Callback;


/**
 * Manejador de requerimientos
 */
public class RequirementManager {

    /**
     * Realiza la llamada a los webservices para aprobar/rechazar un requerimiento
     * @param requirementId Id requerimiento ej 'R1234'
     * @param requirementApproval request para aprobación/rechazo de requerimiento
     * @param callback callbacks de las llamadas al WS
     */
    public static void updateApprovalStatus(String requirementId, RequirementApproval
            requirementApproval, Callback<Requirement> callback){
        RestEndpointFactory.create(IRequirementsEndpoint.class, new CredentialManager
                (AppPreferences.getApplicationContext())
                .generateApiCredentials())
                .updateApprovalStatus(requirementId, requirementApproval)
                .enqueue(callback);
    }
}
