package org.mobicents.ipbx.session.call.model;

import java.util.HashMap;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.mobicents.ipbx.entity.PstnGatewayAccount;
import org.mobicents.ipbx.session.configuration.PbxConfiguration;

@Name("callParticipantManager")
@Scope(ScopeType.APPLICATION)
@Startup
public class CallParticipantManager {
	@In PbxConfiguration pbxConfiguration;
	private HashMap<String, CallParticipant> callParticipants =
		new HashMap<String, CallParticipant>();
	
	synchronized public CallParticipant getCallParticipant(String uri) {

		String newUri = unifyUri(uri);
		if(callParticipants.get(newUri) == null) {
			CallParticipant p = new CallParticipant();
			p.setUri(newUri);
			callParticipants.put(newUri, p);
			
			// If this is a phone number, set up auth 
			if(!uri.startsWith("sip")) {
				p.setName(uri);
				PstnGatewayAccount account = pbxConfiguration.getPstnAccounts().get(0);
				p.setPstnGatewayAccount(account);
			}
		}
		return callParticipants.get(newUri);
	}
	
	synchronized public CallParticipant getExistingCallParticipant(String uri) {
		uri = unifyUri(uri);
		return callParticipants.get(uri);
	}
	
	synchronized public void removeCallParticipant(String uri) {
		uri = unifyUri(uri);
		callParticipants.remove(uri);
	}
	
	private String unifyUri(String uri) {
		if(!uri.startsWith("sip")) {
			StringBuffer newUri = new StringBuffer();
			for(int q=0; q<uri.length(); q++) {
				char c = uri.charAt(q);
				if(c >= '0' && c <= '9') {
					newUri.append(c);
				}
			}
			PstnGatewayAccount account = pbxConfiguration.getPstnAccounts().get(0);
			uri = "sip:" + newUri + "@" + account.getHostname();
		}
		return uri;
	}
}
