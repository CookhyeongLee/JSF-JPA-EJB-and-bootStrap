/*****************************************************************
 * File: PersonPojo.java Course materials (22W) CST8277
 *
 * @author Teddy Yap
 * @author Shahriar (Shawn) Emami
 * @author (original) Mike Norman
 */
package databank.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import databank.ejb.PersonEJB;
import databank.model.PersonPojo;

/**
 * Description: Implements the C-R-U-D API for the database
 * 
 * [TODO] 01 - some components are managed by CDI.<br>
 * [TODO] 02 - methods which perform DML need @Transactional annotation.<br>
 * [TODO] 03 - fix the syntax errors to correct methods. <br>
 * [TODO] 04 - refactor this class. move all the method bodies and EntityManager to a new service class which is a
 * singleton (EJB).<br>
 * [TODO] 05 - inject the service class using EJB.<br>
 * [TODO] 06 - call all the methods of service from each appropriate method here.
 */
@Named
@ApplicationScoped
public class PersonDaoImpl implements PersonDao, Serializable {
	/** explicitly set serialVersionUID */
	private static final long serialVersionUID = 1L;

	//get the log4j2 logger for this class
	private static final Logger LOG = LogManager.getLogger();

	@EJB
    private PersonEJB personEJB;
	
	@Override
	public List< PersonPojo> readAllPeople() {
		LOG.debug( "PersonDao -> reading all people");
		return personEJB.readAllPeople();
	}

	@Transactional
	@Override
	public PersonPojo createPerson( PersonPojo person) {
		LOG.debug( "PersonDao -> creating a person = {}", person);
		return personEJB.createPerson(person);
	}

	@Override
	public PersonPojo readPersonById( int personId) {
		LOG.debug( "PersonDao -> read a specific person = {}", personId);
		return personEJB.readPersonById(personId);
	}

	@Transactional
	@Override
	public PersonPojo updatePerson( PersonPojo personWithUpdates) {
		LOG.debug( "PersonDao -> updating a specific person = {}", personWithUpdates);
		return personEJB.updatePerson(personWithUpdates);
	}

	@Transactional
	@Override
	public void deletePersonById( int personId) {
		LOG.debug( "PersonDao -> deleting a specific personID = {}", personId);
		personEJB.deletePersonById(personId);
	}

}