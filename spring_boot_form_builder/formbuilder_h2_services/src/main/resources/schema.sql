CREATE TABLE ui_app (ui_app_id integer Not null, name Varchar(50), display_name Varchar(50));
CREATE TABLE ui_form( id integer Not Null, form_table_name Varchar(100), order_by integer, display_name Varchar(100), name_column_display_name Varchar(50), group_by Varchar(30), ui_app_id integer);
CREATE TABLE ui_form_link( ui_form_id integer Not Null, ui_form_link_id integer, link_name Varchar(50), single_select boolean);
CREATE TABLE ui_rule ( ui_form_id integer Not null, clause Varchar(500));
CREATE Table citizenship_rule_lookup (id Integer,
citizenship_date Date,
team_member_id integer,
citizen_id integer
);
CREATE Table team_member (id Integer,
name Varchar(50),
emp_code Varchar(50)
);
CREATE Table country (id Integer,
name Varchar(50),
country_code Varchar(50)
);
CREATE Table sow (id Integer,
name Varchar(50),
description Varchar(50)
);
CREATE Table project (id Integer,
name Varchar(50),
project_type Varchar(50),
start_date Date
);
CREATE Table project_supplier_relationship (
project_id integer,
supplier_id integer
);
CREATE Table project_sow_relationship (
project_id integer,
sow_id integer
);
CREATE Table project_rule_lookup (id Integer,
citizenship_date Date,
project_id integer,
supplier_id integer,
citizen_id integer
);
CREATE Table supplier (id Integer,
name Varchar(50),
supp_code Varchar(50)
);
CREATE Table supplier_location_relationship (
supplier_id integer,
location_id integer
);

