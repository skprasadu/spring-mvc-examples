insert into ui_app values (1, 'vendor_management', 'Vendor Management');

insert into Ui_Form values(1, 'citizenship_rule_lookup', 1, 'Citizenship Rule Lookup', '', 'Rule', 1 );
insert into Ui_Form_Link values(1, 2, '', false);
insert into Ui_Form_Link values(1, 3, 'citizen', false);

insert into Ui_Form values(2, 'team_member', 2, 'Team Member', '', 'Form', 1 );

insert into Ui_Form values(4, 'country', 3, 'Country', '', 'Form', 1 );

insert into Ui_Form values(5, 'sow', 4, 'Sow', '', 'Form', 1 );

insert into Ui_Form values(6, 'project', 5, 'Project', '', 'Form', 1 );
insert into Ui_Form_Link values(6, 7, '', true);
insert into Ui_Form_Link values(6, 5, '', true);

insert into Ui_Form values(8, 'project_rule_lookup', 6, 'Project Rule Lookup', '', 'Rule', 1 );
insert into Ui_Form_Link values(8, 6, '', false);
insert into Ui_Form_Link values(8, 7, '', false);
insert into Ui_Form_Link values(8, 3, 'citizen', false);

insert into Ui_Form values(7, 'supplier', 7, 'Supplier', '', 'Form', 1 );
insert into Ui_Form_Link values(7, 9, 'location', true);
