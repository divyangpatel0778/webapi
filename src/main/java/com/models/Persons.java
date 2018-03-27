package com.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name = "persons")
// @NamedQuery(name="persons.findAll", query="SELECT p FROM persons p")
public class Persons implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PER_ID")
	private Integer id;

	@Column(name = "PER_PERSON_ID")
	private Integer personId;

	@Column(name = "PER_FIRST_NAME")
	private String firstName;

	@Column(name = "PER_MIDDLE_NAME")
	private String middleName;

	@Column(name = "PER_LAST_NAME")
	private String lastName;

	@Column(name = "PER_SUFFIX")
	private String suffix;

	@Column(name = "PER_PREFIX")
	private String prefix;

	@Column(name = "PER_PREFER_NAME")
	private String preferName;

	@Column(name = "PER_GENDER")
	private String gender;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PER_DOB")
	private Date dob;

	@Column(name = "PER_PHOTO_URL")
	private String photoUrl;

	@Column(name = "PER_RECORD_STATUS_FLAG")
	private String recordStatusFlag;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PER_RECORD_STATUS_DATE")
	private Date recordStatusDate;

	@Column(name = "PER_CREATED_USER_ID")
	private Integer createdUserId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PER_CREATED_DATE")
	private Date createdDate;

	@Column(name = "PER_LAST_CHANGED_USER_ID")
	private Integer lastChangedUserID;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PER_LAST_CHANGED_DATE")
	private Date lastChangedDate;

	@Column(name = "PER_TRANSACTION_GUID")
	private Character transactionGuid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PER_RECORD_TIMESTAMP")
	private Date recordTimeStamp;

	@Column(name = "PER_QC_FLAG_DONE")
	private String qcFlagDone;

	@Column(name = "PER_PHONE")
	private String phone;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getPreferName() {
		return preferName;
	}

	public void setPreferName(String preferName) {
		this.preferName = preferName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getRecordStatusFlag() {
		return recordStatusFlag;
	}

	public void setRecordStatusFlag(String recordStatusFlag) {
		this.recordStatusFlag = recordStatusFlag;
	}

	public Date getRecordStatusDate() {
		return recordStatusDate;
	}

	public void setRecordStatusDate(Date recordStatusDate) {
		this.recordStatusDate = recordStatusDate;
	}

	public Integer getCreatedUserId() {
		return createdUserId;
	}

	public void setCreatedUserId(Integer createdUserId) {
		this.createdUserId = createdUserId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getLastChangedUserID() {
		return lastChangedUserID;
	}

	public void setLastChangedUserID(Integer lastChangedUserID) {
		this.lastChangedUserID = lastChangedUserID;
	}

	public Date getLastChangedDate() {
		return lastChangedDate;
	}

	public void setLastChangedDate(Date lastChangedDate) {
		this.lastChangedDate = lastChangedDate;
	}

	public Character getTransactionGuid() {
		return transactionGuid;
	}

	public void setTransactionGuid(Character transactionGuid) {
		this.transactionGuid = transactionGuid;
	}

	public Date getRecordTimeStamp() {
		return recordTimeStamp;
	}

	public void setRecordTimeStamp(Date recordTimeStamp) {
		this.recordTimeStamp = recordTimeStamp;
	}

	public String getQcFlagDone() {
		return qcFlagDone;
	}

	public void setQcFlagDone(String qcFlagDone) {
		this.qcFlagDone = qcFlagDone;
	}

	public String getFullName() {
		String name = getFirstName() + " " + (StringUtils.isNotBlank(getMiddleName()) ? (getMiddleName() + " ") : "")
				+ getLastName();

		return name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
