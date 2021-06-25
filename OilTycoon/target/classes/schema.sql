CREATE TABLE IF NOT EXISTS wells (
                                         id      INTEGER PRIMARY KEY AUTOINCREMENT,
                                         name    VARCHAR (32) NOT NULL UNIQUE
);
CREATE TABLE IF NOT EXISTS equipment (
                                         id      INTEGER PRIMARY KEY AUTOINCREMENT,
                                         name    VARCHAR (32) NOT NULL UNIQUE,
                                         well_id INTEGER NOT NULL REFERENCES wells(id)
);