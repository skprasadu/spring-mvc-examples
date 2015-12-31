CREATE TABLE ui_app (ui_app_id integer Not null, name Varchar(50), display_name Varchar(50));
CREATE TABLE ui_form( id integer Not Null, form_table_name Varchar(100), order_by integer, display_name Varchar(100), name_column_display_name Varchar(50), group_by Varchar(30), ui_app_id integer, validator Varchar(50), validator_inputs Varchar(500));
CREATE TABLE ui_form_link( ui_form_id integer Not Null, ui_form_link_id integer, link_name Varchar(50), single_select boolean);
CREATE TABLE uiRules( id integer Not Null, uiRuleId Varchar(50), rule Varchar(500));
CREATE Table project (id Integer,
name Varchar(50),
project_type Varchar(50),
start_date Date,
end_date Date
);
CREATE Table supplier (id Integer,
name Varchar(50),
supp_code Varchar(50)
);
CREATE Table supplier_country_relationship (
supplier_id integer,
country_id integer
);
CREATE Table sow (id Integer,
name Varchar(50),
description Varchar(50)
);
CREATE Table country (id Integer,
name Varchar(50),
country_code Varchar(50)
);
CREATE Table team_member (id Integer,
name Varchar(50),
emp_code Varchar(50)
);
CREATE Table project_rule_lookup (
project_id integer Not Null,
supplier_id integer,
country_id integer
);
CREATE Table citizenship_rule_lookup (
team_member_id integer Not Null,
country_id integer
);
