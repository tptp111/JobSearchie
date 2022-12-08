DROP VIEW IF EXISTS user_full;

CREATE VIEW IF NOT EXISTS user_full AS SELECT 
	user.id AS id,
	user.accountType AS accountType,
	user.firstName AS firstName,
	user.lastName AS lastName,
	user.email AS email,
	user.password AS password,
	user.locationId AS locationId,
	user.contactNumber AS contactNumber,
	user.dateCreated AS dateCreated,
	user.dateOfBirth AS  dateOfBirth,
	user.currentJobName AS currentJobName,
	user.currentJobLevel AS currentJobLevel,
	user.expectedCompensation AS expectedCompensation,
	user.resumeDir AS resumeDir,
	user.coverLetterDir AS coverLetterDir,
	user.companyName AS companyName,
	user.recruitingSpecialty AS  recruitingSpecialty,
	location.country AS country,
	location.state AS state,
	location.city AS city,
	location.postcode AS postcode
	FROM user INNER JOIN location ON user.locationId = location.id;