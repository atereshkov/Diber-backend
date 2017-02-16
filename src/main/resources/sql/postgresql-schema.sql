CREATE TABLE "users" (
	"id" serial NOT NULL,
	"username" VARCHAR(255) NOT NULL,
	"email" VARCHAR(255) NOT NULL,
	"password" VARCHAR(255) NOT NULL,
	"enabled" BOOLEAN NOT NULL,
	"fullname" VARCHAR(255) NOT NULL,
	CONSTRAINT users_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "roles" (
	"id" serial NOT NULL,
	"name" VARCHAR(255) NOT NULL,
	CONSTRAINT roles_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "user_roles" (
	"user_id" integer NOT NULL,
	"role_id" integer NOT NULL
) WITH (
  OIDS=FALSE
);



CREATE TABLE "orders" (
	"id" serial NOT NULL,
	"shop_id" integer NOT NULL,
	"date" DATE NOT NULL,
	"status" TEXT NOT NULL,
	"courier_id" integer NOT NULL,
	"description" VARCHAR(255) NOT NULL,
	"delivery_price" FLOAT NOT NULL,
	"customer_id" integer NOT NULL,
	CONSTRAINT orders_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "shops" (
	"id" serial NOT NULL,
	"name" TEXT NOT NULL,
	"address" TEXT NOT NULL,
	CONSTRAINT shops_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "reviews" (
	"id" serial NOT NULL,
	"review" VARCHAR(255) NOT NULL,
	"rating" integer NOT NULL,
	"user_id" integer NOT NULL,
	CONSTRAINT reviews_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "requests" (
	"id" serial NOT NULL,
	"courier_id" integer NOT NULL,
	"order_id" integer NOT NULL
) WITH (
  OIDS=FALSE
);



CREATE TABLE "addresses" (
	"id" serial NOT NULL,
	"country" VARCHAR(255) NOT NULL,
	"city" VARCHAR(255) NOT NULL,
	"address" VARCHAR(255) NOT NULL,
	"region" VARCHAR(255) NOT NULL,
	"postal_code" integer NOT NULL,
	"phone" VARCHAR(255) NOT NULL,
	"user_id" integer NOT NULL,
	"name" VARCHAR(255) NOT NULL,
	CONSTRAINT addresses_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);




ALTER TABLE "user_roles" ADD CONSTRAINT "user_roles_fk0" FOREIGN KEY ("user_id") REFERENCES "users"("id");
ALTER TABLE "user_roles" ADD CONSTRAINT "user_roles_fk1" FOREIGN KEY ("role_id") REFERENCES "roles"("id");

ALTER TABLE "orders" ADD CONSTRAINT "orders_fk0" FOREIGN KEY ("shop_id") REFERENCES "shops"("id");
ALTER TABLE "orders" ADD CONSTRAINT "orders_fk1" FOREIGN KEY ("courier_id") REFERENCES "users"("id");
ALTER TABLE "orders" ADD CONSTRAINT "orders_fk2" FOREIGN KEY ("customer_id") REFERENCES "users"("id");


ALTER TABLE "reviews" ADD CONSTRAINT "reviews_fk0" FOREIGN KEY ("user_id") REFERENCES "users"("id");

ALTER TABLE "requests" ADD CONSTRAINT "requests_fk0" FOREIGN KEY ("courier_id") REFERENCES "users"("id");
ALTER TABLE "requests" ADD CONSTRAINT "requests_fk1" FOREIGN KEY ("order_id") REFERENCES "orders"("id");

ALTER TABLE "addresses" ADD CONSTRAINT "addresses_fk0" FOREIGN KEY ("user_id") REFERENCES "users"("id");



-- for oauth data
CREATE TABLE IF NOT EXISTS oauth_client_details (
  client_id VARCHAR(256) PRIMARY KEY,
  resource_ids VARCHAR(256),
  client_secret VARCHAR(256),
  scope VARCHAR(256),
  authorized_grant_types VARCHAR(256),
  web_server_redirect_uri VARCHAR(256),
  authorities VARCHAR(256),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(256)
);

CREATE TABLE IF NOT EXISTS oauth_client_token (
  token_id VARCHAR(256),
  token bytea,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256)
);

CREATE TABLE IF NOT EXISTS oauth_access_token (
  token_id VARCHAR(256),
  token bytea,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256),
  authentication bytea,
  refresh_token VARCHAR(256)
);

CREATE TABLE IF NOT EXISTS oauth_refresh_token (
  token_id VARCHAR(256),
  token bytea,
  authentication bytea
);

CREATE TABLE IF NOT EXISTS oauth_code (
  code VARCHAR(256), authentication bytea
);

CREATE TABLE IF NOT EXISTS oauth_approvals (
	userId VARCHAR(256),
	clientId VARCHAR(256),
	scope VARCHAR(256),
	status VARCHAR(10),
	expiresAt TIMESTAMP,
	lastModifiedAt TIMESTAMP
);