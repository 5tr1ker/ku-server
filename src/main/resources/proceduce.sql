DELIMITER $$
CREATE PROCEDURE AddForeignKeyIfNotExists()
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.TABLE_CONSTRAINTS
        WHERE CONSTRAINT_NAME = 'FK2dc2b0wtmsm0rlc0t4oiggjr6'
    ) THEN
        alter table account add constraint FK2dc2b0wtmsm0rlc0t4oiggjr6 foreign key (default_delivery_address_delivery_address_id) references delivery_address (delivery_address_id);
END IF;

    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.TABLE_CONSTRAINTS
        WHERE CONSTRAINT_NAME = 'FKdejsejffr5v1m2mi44rx270v2'
    ) THEN
        alter table menu_option_container add constraint FKdejsejffr5v1m2mi44rx270v2 foreign key (menu_menu_id) references menu (menu_id);
END IF;

END
$$ DELIMITER ;