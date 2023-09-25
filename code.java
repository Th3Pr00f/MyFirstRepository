
public static boolean isPaymeFlagEnabled(LocalObject tranObject) {
		boolean flag = false;
		if(PaymentConstants.pymnt_active == tranObject.getInt(PaymentConstants.PAYME_INSTITUTION_FLAG) && 
				PaymentConstants.pymnt_active == tranObject.getInt(PaymentConstants.PAYME_TERMINAL_FLAG)) {
			flag = true;
		}
		return flag;
	}
	
public static String formatPaymeDataElement105(LocalObject tranObject) {
    	StringBuilder sb = null;
    	String text = null;
    	String purpose = null;
    	String payeeName = null;
    	String paymentDetails = null;
    	String udf5 = null;
    	String result = "";
    	try {
    		sb = new StringBuilder();
    		udf5 = tranObject.getString(PaymentConstants.pymnt_usr_def_fld5);
    		if (!StringUtils.hasText(udf5)) {
    			return "";
    		}
    		paymentDetails = udf5.trim().substring("ptlf ".length());
    		int pipePosition = paymentDetails.indexOf("|");
    		text = paymentDetails.substring(0, pipePosition);
    		paymentDetails= paymentDetails.substring(pipePosition+1);
    		
    		pipePosition = paymentDetails.indexOf("|");
    		purpose = paymentDetails.substring(0, pipePosition);
    		paymentDetails= paymentDetails.substring(pipePosition+1);
    		
    		payeeName = paymentDetails;
    		
    		sb.append(StringUtils.hasText(text)?trimMultiSpaceToOne(text.trim()) : "");
    		sb.append("|");
    		sb.append(StringUtils.hasText(purpose)?purpose.trim() : "");
    		sb.append("|");
    		sb.append(StringUtils.hasText(payeeName)?trimMultiSpaceToOne(payeeName.trim()) : "");
    		if (sb.length() >= 50) {
    			result = sb.substring(0, 50);
    		} else {
    			result = sb.toString();
    		}
   			tranObject.put(PaymentConstants.PAYME_DE105, result);
   			
    		formatPaymeResponseUDF5(tranObject);
    	} catch (Exception e) {
			PGLogger.logTrace("Problem occurred while formatting PayMe DE105", e);
		} finally {
			sb = null;
	    	purpose = null;
	    	payeeName = null;
	    	paymentDetails = null;
	    	udf5 = null;
		}
    	return result;
    }
	
public static String formatPaymeResponseUDF5(LocalObject tranObject) {
    	StringBuilder sb = null;
    	String text = null;
    	String purpose = null;
    	String payeeName = null;
    	String bankName = null;
    	String payerName = null;
    	String paymentDetails = null;
    	String udf5 = null;
    	String result = null;
    	try {
    		sb = new StringBuilder();
    		udf5 = tranObject.getString(PaymentConstants.pymnt_usr_def_fld5);
    		if (!StringUtils.hasText(udf5)) {
    			return "";
    		}
    		bankName = tranObject.getString(PaymentConstants.TENANT_NAME);
    		payerName = tranObject.getString(PaymentConstants.CAS_CARD_HOLDER_NAME);
    		
    		paymentDetails = udf5.trim().substring("ptlf ".length());
    		int pipePosition = paymentDetails.indexOf("|");
    		text = paymentDetails.substring(0, pipePosition);
    		paymentDetails= paymentDetails.substring(pipePosition+1);
    		
    		pipePosition = paymentDetails.indexOf("|");
    		purpose = paymentDetails.substring(0, pipePosition);
    		paymentDetails= paymentDetails.substring(pipePosition+1);
    		
    		payeeName = paymentDetails;
    		
    		sb.append("ptlf ");
    		sb.append(StringUtils.hasText(text)?text : "");
    		sb.append("|");
    		sb.append(StringUtils.hasText(purpose)?purpose : "");
    		sb.append("|");
    		sb.append(StringUtils.hasText(payeeName)?payeeName : "");
    		sb.append("|");
    		sb.append(StringUtils.hasText(bankName)?bankName : "");
    		sb.append("|");
    		sb.append(StringUtils.hasText(payerName)?payerName : "");
    		
    		if (sb.length() >= 255) {
    			result = sb.substring(0, 255);
    		} else {
    			result = sb.toString();
    		}
    		
    		tranObject.put(PaymentConstants.PAYME_UDF5, result);
    	} catch (Exception e) {
			PGLogger.logTrace("Problem occurred while formatting PayMe UDF5 data element", e);
		} finally {
			purpose = null;
	    	payeeName = null;
	    	bankName = null;
	    	payerName = null;
	    	paymentDetails = null;
	    	udf5 = null;
		}
    	return result;
    }
	
public static boolean hasArabicCharacters(String text) {
		boolean flag = false;
		String arabicPattern = null;
		Pattern pattern = null;
		Matcher matcher = null;
		try {
			arabicPattern = ".*[\\u0600-\\u06FF]+.*";
			pattern = Pattern.compile(arabicPattern);
			matcher = pattern.matcher(text);
			if (matcher.matches()) {
				flag = true;
			} else {
				String data = decodeText(text);
				flag = pattern.matcher(data).matches()?true:(pattern.matcher(decodeText(data)).matches()?true:false);
			}
		} catch (Exception e) {
			PGLogger.logTrace("Problem occurred while validating UDF5 :: " + text, e);
		}
		return flag;
	}

			paymeIndictaor = padSpaces("", 2);
	        if (StringUtils.hasText(udf5) && TransactionValidationService.isPaymeFlagEnabled(tranObject) && 
	        		TransactionValidationService.isValidPaymeUdf5(udf5)) {
	        	message.setElement(105, TransactionValidationService.formatPaymeDataElement105(tranObject));
	        	paymeIndictaor = "QP";
	        }
	        else if(udf5.startsWith("ptlf ") && udf5.contains(" ")){
	        	if(udf5 != null && udf5.length() > 1)
	        	{
	        		ptlfKeywordCheckvalue = "ptlf ";
	        		if(ptlfKeywordCheckvalue.equalsIgnoreCase(udf5.substring(0,5))){
	        			String subString = udf5.replaceAll("\\s","").substring(4); 		
	        			if(subString.length() > 50){
	        				message.setElement(105, subString.substring(0, 50));
	        			}
	        			else{
	        				message.setElement(105, subString);
	        			}
	        		}
	        	}
	        }

String prmMessage = otpIndicatior + ipAddress + kfastFlag + pymntlnkflg + p2pflg + UDF5 + sibatch + custvalid + pinlessApplepayIdentifier+ applepayIdentifier;
			prmMessage = padSpaces(prmMessage, 35) + paymeIndictaor + tempSpace;
			prmMessage = prmMessage.substring(0, 50);

if (TransactionValidationService.isPaymeFlagEnabled(paymentObject) && TransactionValidationService.isValidPaymeUdf5(payForm.getUdf5())) {
						PGLogger.logTrace(ResourceBundleUtil.getString("pg.tran.payme.validation.success.msg")+ " :: " + payForm.getUdf5() + " :: " + payForm.getId() + " :: " + payForm.getTrackid(),PaymentConstants.log_transaction_info);
						PGLogger.logTrace("Skip EBILL transaction process. EBILL Error Flag :: " + ebilErrorFlag,PaymentConstants.log_transaction_info);
						paymentObject.remove("error");
     					paymentObject.remove(PaymentConstants.pymnt_tran_description);
     					paymentObject.remove(PaymentConstants.EBILL_AR_ERRORCODE);
					}

public String pointsRedemValAuthValmakexml(String PAN) {
		StringBuilder builder = null;
		StringBuilder inxml = new StringBuilder();

		inxml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n");
		inxml.append("<EmvTokenSearchCriteria>\r\n");
		inxml.append("<PAN>" + PAN + "</PAN>\r\n");
		inxml.append("<PSN>00</PSN>\r\n");
		inxml.append("<Active>true</Active>\r\n");
		inxml.append("<Blocked>false</Blocked>\r\n");
		inxml.append("<Locked>false</Locked>\r\n");
		inxml.append("<Revoked>false</Revoked>\r\n");
		inxml.append("<Initialized>true</Initialized>\r\n");
		inxml.append("<Match>all</Match>\r\n");
		inxml.append("<StartIndex>1</StartIndex>\r\n");
		inxml.append("<MaxResults>1</MaxResults>\r\n");
		inxml.append("<ResultFormat>full</ResultFormat>\r\n");
		inxml.append("</EmvTokenSearchCriteria>");

		builder = new StringBuilder(inxml);
		builder.replace(builder.indexOf("<PAN>") + 11, builder.indexOf("</PAN>") - 4, "******");
		PGLogger.logTrace("Customer Reference Card Encryption Request :: " + builder.toString(), PaymentConstants.log_transaction_info);

		return inxml.toString();
	}