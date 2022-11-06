package bankprojekt.verarbeitung;

/**
 * alle angebotenen Kontoarten
 * @author Admin
 *
 */
public enum Kontoart {
	GIROKONTO("mit ganz hohem Dispo"), 
	SPARBUCH("mit ganz vielen Zinsen"), 
	FESTGELDKONTO("Ham wa noch nich");
	
	/**
	 * @return the werbebotschaft
	 */
	public String getWerbebotschaft() {
		return this.werbebotschaft;
	}

	private String werbebotschaft;
	
	Kontoart(String werbung) {
		this.werbebotschaft = werbung;
	}
}
