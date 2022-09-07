/*****************************************************************
 * File: PersonPojoListener.java Course materials (22W) CST8277
 *
 * @author Teddy Yap
 * @author Shahriar (Shawn) Emami
 * @author (original) Mike Norman
 */
package databank.model;

import java.time.LocalDateTime;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * [TODO] 21 - What annotations should be used on these method.
 * https://www.logicbig.com/tutorials/java-ee-tutorial/jpa/entity-listeners.html<br>
 */
public class PersonPojoListener {

	//[TODO] 22 - called before persist to add the dates
	@PrePersist
	public void setCreatedOnDate( PersonPojo person) {
		LocalDateTime now = LocalDateTime.now();
		person.setCreated( now);
		//might as well call setUpdated as well
		person.setUpdated( now);
	}

	//[TODO] 23 - called before update to update the date
	@PreUpdate
	public void setUpdatedDate( PersonPojo person) {
		person.setUpdated( LocalDateTime.now());
	}

}