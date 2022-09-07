/*****************************************************************
 * File: PersonPojo.java Course materials (22W) CST8277
 *
 * @author Teddy Yap
 * @author Shahriar (Shawn) Emami
 * @author (original) Mike Norman
 */
package databank.jsf;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import databank.dao.PersonDao;
import databank.model.PersonPojo;

/**
 * <p>
 * Description: Responsible for collection of Person Pojo's in XHTML (list) <h:dataTable> </br>
 * Delegates all C-R-U-D behavior to DAO
 * </p>
 * 
 * <p>
 * This class is complete
 * </p>
 */
@Named("personController")
@SessionScoped
public class PersonController implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LogManager.getLogger();

	public static final String UICONSTS_BUNDLE_EXPR = "#{uiconsts}";
	public static final String PERSON_MISSING_REFRESH_BUNDLE_MSG = "refresh";
	public static final String PERSON_OUTOFDATE_REFRESH_BUNDLE_MSG = "outOfDate";

	@Inject
	protected FacesContext facesContext;

	@Inject
	protected PersonDao personDao;

	@Inject
	@ManagedProperty( UICONSTS_BUNDLE_EXPR)
	protected ResourceBundle uiconsts;

	protected List< PersonPojo> people;
	protected boolean adding;

	public void loadPeople() {
		LOG.debug( "loadPeople");
		people = personDao.readAllPeople();
	}

	public List< PersonPojo> getPeople() {
		return this.people;
	}

	public void setPersons( List< PersonPojo> people) {
		this.people = people;
	}

	public boolean isAdding() {
		return adding;
	}

	public void setAdding( boolean adding) {
		this.adding = adding;
	}

	/**
	 * Toggles the add person mode which determines whether the addPerson form is rendered
	 */
	public void toggleAdding() {
		this.adding = !this.adding;
	}

	public String editPerson( PersonPojo person) {
		LOG.debug( "editPerson={}", person);
		person.setEditable( true);
		return null;
	}

	public String updatePerson( PersonPojo personWithEdits) {
		LOG.debug( "updatePerson={}", personWithEdits);
		PersonPojo personToBeUpdated = personDao.readPersonById( personWithEdits.getId());
		if ( personToBeUpdated == null) {
			LOG.debug( "FAILED update Person, does not exists = {}", personToBeUpdated);
			facesContext.addMessage( null, new FacesMessage( FacesMessage.SEVERITY_ERROR,
					uiconsts.getString( PERSON_MISSING_REFRESH_BUNDLE_MSG), null));
		} else {
			personToBeUpdated = personDao.updatePerson( personWithEdits);
			if ( personToBeUpdated == null) {
				LOG.debug( "FAILED update Person={}", personToBeUpdated);
				facesContext.addMessage( null, new FacesMessage( FacesMessage.SEVERITY_ERROR,
						uiconsts.getString( PERSON_OUTOFDATE_REFRESH_BUNDLE_MSG), null));
			} else {
				personToBeUpdated.setEditable( false);
				int idx = people.indexOf( personWithEdits);
				people.remove( idx);
				people.add( idx, personToBeUpdated);
			}
		}
		return null;
	}

	public String cancelUpdate( PersonPojo person) {
		LOG.debug( "cancelUpdate={}", person);
		person.setEditable( false);
		return null;
	}

	public void deletePerson( int personId) {
		LOG.debug( "deletePerson={}", personId);
		PersonPojo personToBeRemoved = personDao.readPersonById( personId);
		if ( personToBeRemoved == null) {
			LOG.debug( "failed deletePerson does not exists={}", personId);
			return;
		}
		personDao.deletePersonById( personId);
		people.remove( personToBeRemoved);

	}

	public void addNewPerson( PersonPojo person) {
		LOG.debug( "adding new Person={}", person);
		PersonPojo newPerson = personDao.createPerson( person);
		people.add( newPerson);
	}

	public String refreshPersonForm() {
		LOG.debug( "refreshPersonForm");
		Iterator< FacesMessage> facesMessageIterator = facesContext.getMessages();
		while ( facesMessageIterator.hasNext()) {
			facesMessageIterator.remove();
		}
		return "index.xhtml?faces-redirect=true";
	}

}