/*****************************************************************
 * File: PersonELB.java Course materials (22W) CST8277
 *
 * @author Cookhyeong Lee
 */
package databank.ejb;

import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import databank.model.PersonPojo;

/**
 * Description: Implements the C-R-U-D API for the database
 * 
 * [TODO] 04 - create this class. move all the method bodies and EntityManager to a new service class which is a
 * singleton (EJB).<br>
 * This class should not contain any @Transactional as that is not supposed to be in EJB
 */
@Singleton
public class PersonEJB {
	//get the log4j2 logger for this class
	private static final Logger LOG = LogManager.getLogger();

	@PersistenceContext(unitName = "PU_DataBank")
	protected EntityManager em;
	
	public List< PersonPojo> readAllPeople() {
		LOG.debug( "reading all people");
		//use the named JPQL query from the PersonPojo class to grab all the people
		TypedQuery< PersonPojo> allPeopleQuery = em.createNamedQuery( PersonPojo.PERSON_FIND_ALL, PersonPojo.class);
		//execute the query and return the result/s.
		return allPeopleQuery.getResultList();
	}

	public PersonPojo createPerson( PersonPojo person) {
		LOG.debug( "creating a person = {}", person);
		em.persist( person);
		return person;
	}

	public PersonPojo readPersonById( int personId) {
		LOG.debug( "read a specific person = {}", personId);
		return em.find( PersonPojo.class, personId);
	}

	public PersonPojo updatePerson( PersonPojo personWithUpdates) {
		LOG.debug( "updating a specific person = {}", personWithUpdates);
		PersonPojo mergedPersonPojo = em.merge( personWithUpdates);
		return mergedPersonPojo;
	}

	public void deletePersonById( int personId) {
		LOG.debug( "deleting a specific personID = {}", personId);
		PersonPojo person = readPersonById( personId);
		LOG.debug( "deleting a specific person = {}", person);
		if ( person != null) {
			em.refresh( person);
			em.remove( person);
		}
	}

}
