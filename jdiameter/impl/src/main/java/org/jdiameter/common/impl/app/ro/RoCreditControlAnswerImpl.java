/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jdiameter.common.impl.app.ro;

import org.jdiameter.api.Answer;
import org.jdiameter.api.Avp;
import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.Request;
import org.jdiameter.api.ro.events.RoCreditControlAnswer;
import org.jdiameter.common.impl.app.AppAnswerEventImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ...
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a> 
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a> 
 */
public class RoCreditControlAnswerImpl extends AppAnswerEventImpl implements RoCreditControlAnswer {

  private static final long serialVersionUID = 1L;

  protected Logger logger = LoggerFactory.getLogger(RoCreditControlAnswerImpl.class);

  private static final int CREDIT_CONTROL_FAILURE_HANDLING_AVP_CODE = 427; 
  private static final int DIRECT_DEBITING_FAILURE_HANDLING_AVP_CODE = 428;
  private static final int REQUESTED_ACTION_AVP_CODE = 436;
  private static final int CC_REQUEST_TYPE_AVP_CODE = 416;
  private static final int VALIDITY_TIME_AVP_CODE = 448;

  public RoCreditControlAnswerImpl(Request message, long resultCode) {
    super(message.createAnswer(resultCode));
  }

  public RoCreditControlAnswerImpl(Answer message) {
    super(message);
  }

  public boolean isCreditControlFailureHandlingAVPPresent() {
    return super.message.getAvps().getAvp(CREDIT_CONTROL_FAILURE_HANDLING_AVP_CODE) != null;
  }

  public int getCredidControlFailureHandlingAVPValue() {
    Avp credidControlFailureHandlingAvp = super.message.getAvps().getAvp(CREDIT_CONTROL_FAILURE_HANDLING_AVP_CODE);
    if(credidControlFailureHandlingAvp != null) {
      try {
        return credidControlFailureHandlingAvp.getInteger32();
      }
      catch (AvpDataException e) {
        logger.debug("Failure trying to obtain Credit-Control-Failure-Handling AVP value", e);
      }
    }

    return -1;
  }

  public boolean isDirectDebitingFailureHandlingAVPPresent() {
    return super.message.getAvps().getAvp(DIRECT_DEBITING_FAILURE_HANDLING_AVP_CODE) != null;
  }

  public int getDirectDebitingFailureHandlingAVPValue() {
    Avp directDebitingFailureHandlingAvp = super.message.getAvps().getAvp(DIRECT_DEBITING_FAILURE_HANDLING_AVP_CODE);
    if(directDebitingFailureHandlingAvp != null) {
      try {
        return directDebitingFailureHandlingAvp.getInteger32();
      }
      catch (AvpDataException e) {
        logger.debug("Failure trying to obtain Direct-Debiting-Failure-Handling AVP value", e);
      }
    }

    return -1;
  }

  public Avp getValidityTimeAvp() {
    return super.message.getAvps().getAvp(VALIDITY_TIME_AVP_CODE);
  }

  public boolean isRequestTypeAVPPresent() {
    return super.message.getAvps().getAvp(CC_REQUEST_TYPE_AVP_CODE) != null;
  }

  public int getRequestTypeAVPValue() {
    Avp requestTypeAvp = super.message.getAvps().getAvp(CC_REQUEST_TYPE_AVP_CODE);
    if(requestTypeAvp != null) {
      try {
        return requestTypeAvp.getInteger32();
      }
      catch (AvpDataException e) {
        logger.debug("Failure trying to obtain CC-Request-Type AVP value", e);
      }
    }

    return -1;
  }

  public boolean isRequestedActionAVPPresent() {
    return super.message.getAvps().getAvp(REQUESTED_ACTION_AVP_CODE) != null;
  }

  public int getRequestedActionAVPValue() {
    Avp requestedActionAvp = super.message.getAvps().getAvp(REQUESTED_ACTION_AVP_CODE);
    if(requestedActionAvp != null) {
      try {
        return requestedActionAvp.getInteger32();
      }
      catch (AvpDataException e) {
        logger.debug("Failure trying to obtain Requested-Action AVP value", e);
      }
    }

    return -1;
  }

}