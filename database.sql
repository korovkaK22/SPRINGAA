CREATE TABLE IF NOT EXISTS "queue_lists" (
         queue_id INTEGER NOT NULL,
         user_id INTEGER NOT NULL,
         place_number INTEGER NOT NULL,
         PRIMARY KEY (queue_id, user_id),
         FOREIGN KEY (queue_id) REFERENCES "queues" (id),
         FOREIGN KEY (user_id) REFERENCES "users" (id)
);

CREATE TABLE IF NOT EXISTS "queues" (
        id SERIAL PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        owner_id INTEGER NOT NULL,
        is_open BOOLEAN NOT NULL,
        FOREIGN KEY (owner_id) REFERENCES "users" (id)
);

CREATE TABLE IF NOT EXISTS "users" (
       id SERIAL PRIMARY KEY,
       username VARCHAR(255) NOT NULL,
       password VARCHAR(255) NOT NULL
);