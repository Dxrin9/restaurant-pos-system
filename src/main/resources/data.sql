INSERT IGNORE INTO users (username, password, full_name, role, active) VALUES
('admin','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lha','System Admin','ADMIN',true),
('waiter1','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lha','John Waiter','WAITER',true),
('kitchen1','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lha','Mario Cook','KITCHEN',true),
('bar1','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lha','Lisa Bartender','BAR',true),
('pizza1','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lha','Tony Pizza','PIZZA',true);
INSERT IGNORE INTO restaurant_table (table_number, status) VALUES ('TABLE_1','AVAILABLE'),('TABLE_2','AVAILABLE'),('TABLE_3','AVAILABLE'),('TABLE_4','AVAILABLE'),('TABLE_5','AVAILABLE');
INSERT IGNORE INTO menu_category (name) VALUES ('FOOD'),('DRINKS'),('PIZZA');
INSERT IGNORE INTO menu_item (name, price, station_type, active, category_id) VALUES
('Burger',12.99,'KITCHEN',true,(SELECT id FROM menu_category WHERE name='FOOD')),
('Pasta',11.50,'KITCHEN',true,(SELECT id FROM menu_category WHERE name='FOOD')),
('Salad',8.00,'KITCHEN',true,(SELECT id FROM menu_category WHERE name='FOOD')),
('Beer',4.50,'BAR',true,(SELECT id FROM menu_category WHERE name='DRINKS')),
('Wine',7.00,'BAR',true,(SELECT id FROM menu_category WHERE name='DRINKS')),
('Cocktail',9.50,'BAR',true,(SELECT id FROM menu_category WHERE name='DRINKS')),
('Water',2.00,'BAR',true,(SELECT id FROM menu_category WHERE name='DRINKS')),
('Margherita Pizza',14.00,'PIZZA',true,(SELECT id FROM menu_category WHERE name='PIZZA')),
('Pepperoni Pizza',15.50,'PIZZA',true,(SELECT id FROM menu_category WHERE name='PIZZA')),
('Veggie Pizza',13.00,'PIZZA',true,(SELECT id FROM menu_category WHERE name='PIZZA'));