package com.cogda.common.web;


/**
 * Utility POGO class for creating an object to 
 * easily pass data from the service layer to the 
 * controller and on to the view layer in a 
 * standardized format that meets the needs of 
 * the front-end JavaScript library (JQuery)
 * @author christopher
 */
class AjaxResponseDto {

	/**
	 * Map of errors in human readable form.
	 * Key = the field name.
	 * Value = the translated error message.
	 */
	Map errors = [:]
	
	/**
	 * List of message codes in i18n
	 */
	List messageCodes = []
	
	/**
	 * List of messages in human readable form
	 * as translated from the messageCodes
	 * passed in.
	 */
	private List messages = []

    /**
     * String htmlTemplate when we are passing
     * html fragments back through the response
     * as is the case with the Goal Setting section
     */
    String htmlTemplate

	/**
	 * Adds a message to the private
	 * messages list.
	 * @param message
	 * @return Boolean
	 */
	public Boolean addMessage(String message){
		return this.messages.add(message)
	}

	/**
	 * Returns the list of messages.
	 * @return List<String>
	 */
	public List<String> getMessages(){
		return this.messages
	}
	
	/**
	 * The object that will be passed back to the
	 * view.
	 */
	def modelObject
	
	/** 
	 * Whether the Ajax request succeeded or failed.
	 */
	Boolean success

	/**
	 * Convenience class that indicates whether 
	 * AjaxResponseDto has errors.
	 * @return Boolean
	 */
	Boolean hasErrors(){
		return !this.errors.isEmpty()
	}
	
	/**
	 * Convenience class that indicates whether
	 * AjaxResponseDto has messageCodes.
	 * @return Boolean
	 */
	Boolean hasMessageCodes(){
		return !this.messageCodes.isEmpty()
	}
	
	@Override
	public String toString() {
		return "AjaxResponseDto [errors=" + errors + ", messages=" + messages
				+ ", modelObject=" + modelObject + ", success=" + success + "]";
	}	
	
}
