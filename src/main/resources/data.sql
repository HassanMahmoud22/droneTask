INSERT INTO drones (serial_number, model, weight_limit,battery_capacity,state,weight_loaded) VALUES
    ('1111111', 'Heavyweight', 500,100,'LOADING',50);
INSERT INTO drones (serial_number, model, weight_limit,battery_capacity,state,weight_loaded) VALUES
    ('1111112', 'Lightweight', 100,80,'LOADING',0);
INSERT INTO drones (serial_number, model, weight_limit,battery_capacity,state,weight_loaded) VALUES
    ('1111113', 'Middleweight', 200,100,'LOADING',0);
INSERT INTO drones (serial_number, model, weight_limit,battery_capacity,state,weight_loaded) VALUES
    ('1111114', 'Cruiserweight', 350,20,'IDLE',0);


INSERT INTO medications (medication_name,weight,medication_code,picture_url, drone_id) VALUES
    ('Panadol', 50,'122AGF5','image.png', 1111111);
/*
INSERT INTO medications (medication_name,weight,medication_code,picture_url) VALUES
    ('Panadol2', 150,'122A4GF5','NONE');

INSERT INTO medications (medication_name,weight,medication_code,picture_url) VALUES
    ('Panadol2', 50,'122A3GF5','NONE');

INSERT INTO medications (medication_name,weight,medication_code,picture_url) VALUES
    ('Panadol2', 250,'122A5GF5','NONE');*/