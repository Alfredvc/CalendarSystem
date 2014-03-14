-- Employee data --
INSERT INTO `Employee` VALUES
	(
		'marianne@company.com',
		'A95CD349054E619B28F42F55D1F68D6AE8BB6E22',
		'Marianne Viknes Gruppeløs',
		47982234
	),
	(
		'ola@company.com',
		'A95CD349054E619B28F42F55D1F68D6AE8BB6E22',
		'Marianne Viknes',
		47233434
	),
	(
		'eivind@company.com',
		'A95CD349054E619B28F42F55D1F68D6AE8BB6E22',
		'Eivind No Passion',
		43234354
	),
	(
		'alex@company.com',
		'A95CD349054E619B28F42F55D1F68D6AE8BB6E22',
		'Alexander Moholt',
		93424532
	),
	(
		'mina@company.com',
		'A95CD349054E619B28F42F55D1F68D6AE8BB6E22',
		'Mina Monsen',
		47090912
	),
	(
		'mons@company.com',
		'A95CD349054E619B28F42F55D1F68D6AE8BB6E22',
		'Mons Minason',
		42984522
	),
	(
		'tor@company.com',
		'A95CD349054E619B28F42F55D1F68D6AE8BB6E22',
		'Tor Varden',
		41345423
	),
	(
		'martin@company.com',
		'A95CD349054E619B28F42F55D1F68D6AE8BB6E22',
		'Martin Martinsen',
		42435455
	);

-- Group data --
INSERT INTO `Group` VALUES
    (1, 'Utvikling'),
    (2, 'Drift'),
    (3, 'Vakt'),
    (4, 'Tom gruppe');

-- Group - employee relation --
INSERT INTO `Member_of` VALUES
    (1, 'eivind@company.com'),
    (1, 'alex@company.com'),
    (1, 'mons@company.com'),
    (3, 'ola@company.com'),
    (3, 'tor@company.com'),
    (2, 'mina@company.com'),
    (2, 'martin@company.com');

INSERT INTO `MeetingRoom` VALUES
    (
        'R1',
        'Rom 1',
        10,
        NULL
    ),
    (
        'RA4',
        'Romantikvariatet',
        15,
        NULL
    ),
    (
        'RB2',
        'Romerbrua',
        35,
        NULL
    ),
    (
        'RA',
        'Ramaskrikrommet',
        2,
        NULL
    );

INSERT INTO `Appointment` VALUES
    (
        '9ecea2ce-a839-11e3-93a3-bfeec6879b40',
        'Fløtemøte',
        '2014-02-10 11:00',
        120,
        NULL,
        'R1',
        'tor@company.com'
    ),
    (
        'b650513e-a83b-11e3-8171-7b27abe447b3',
        'Potetgratinering',
        '2014-02-12 14:00',
        80,
        'Fløtepotetverkstedet',
        NULL,
        'mina@company.com'
    ),
    (
        '6940952c-a83e-11e3-86f2-673ce3fc9a1e',
        'Møte om bullshit',
        '2014-02-12 12:00',
        60,
        NULL,
        'RA',
        'alex@company.com'
    );


INSERT INTO `Invited_to` VALUES
    (
        'martin@company.com',
        '6940952c-a83e-11e3-86f2-673ce3fc9a1e',
        FALSE,
        FALSE,
        'Attending'
    ),
    (
        'eivind@company.com',
        '6940952c-a83e-11e3-86f2-673ce3fc9a1e',
        TRUE,
        FALSE,
        'Pending'
    ),
    (
        'ola@company.com',
        'b650513e-a83b-11e3-8171-7b27abe447b3',
        TRUE,
        FALSE,
        'Attending'
    ),
    (
        'mons@company.com',
        'b650513e-a83b-11e3-8171-7b27abe447b3',
        FALSE,
        FALSE,
        'Declined'
    ),
    (
        'alex@company.com',
        'b650513e-a83b-11e3-8171-7b27abe447b3',
        FALSE,
        FALSE,
        'Declined'
    ),
    (
        'martin@company.com',
        'b650513e-a83b-11e3-8171-7b27abe447b3',
        FALSE,
        FALSE,
        'Pending'
    );

INSERT INTO `Notification` VALUES
    (
        1,
        '2014-02-20 10:23',
        'A little test notification',
        'b650513e-a83b-11e3-8171-7b27abe447b3'
    ),
    (
        2,
        '2014-02-21 10:23',
        'Another test notification',
        '6940952c-a83e-11e3-86f2-673ce3fc9a1e'
    );

INSERT INTO `ExternalParticipant` VALUES
    (
        'trulfos@gmail.com',
        'b650513e-a83b-11e3-8171-7b27abe447b3'
    ),
    (
        'eivindbf@gmail.com',
        '6940952c-a83e-11e3-86f2-673ce3fc9a1e'
    );
