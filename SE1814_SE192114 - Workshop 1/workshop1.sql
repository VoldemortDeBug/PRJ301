create database PRJ301_WORKSHOP1

Use database PRJ301_WORKSHOP1

CREATE TABLE tblUsers(
	Username VARCHAR(50) PRIMARY KEY,
	Name VARCHAR(100) NOT NULL,
	Password VARCHAR(255) NOT NULL,
	Role VARCHAR(20) NOT NULL CHECK(Role IN('Founder','Team Member')),
);

CREATE TABLE tblStartupProjects(
	project_id Int primary key,
	project_name VARCHAR(100) not null,
	Description text,
	Status VARCHAR(20) NOT NULL CHECK (Status IN ('Ideation', 'Development', 'Launch', 'Scaling')),
    estimated_launch DATE NOT NULL -- Not null constraint
);

INSERT INTO tblUsers(Username, Name, Password, Role) VALUES
('user1', 'Alice Smith', 'password123', 'Founder'),
('user2', 'Bob Johnson', 'password456', 'Team Member'),
('user3', 'Charlie Brown', 'password789', 'Founder'),
('user4', 'Diana Prince', 'password101', 'Team Member'),
('user5', 'Ethan Hunt', 'password202', 'Founder'),
('user6', 'Fiona Gallagher', 'password303', 'Team Member'),
('user7', 'George Clooney', 'password404', 'Founder'),
('user8', 'Hannah Montana', 'password505', 'Team Member'),
('user9', 'Ian Malcolm', 'password606', 'Founder'),
('user10', 'Jane Doe', 'password707', 'Team Member');

INSERT INTO tblStartupProjects (project_id, project_name, description, status, estimated_launch) VALUES
(1, 'Project Alpha', 'A project in the ideation phase.', 'Ideation', '2024-01-15'),
(2, 'Project Beta', 'Currently under development.', 'Development', '2024-06-30'),
(3, 'Project Gamma', 'Ready for launch.', 'Launch', '2024-03-01'),
(4, 'Project Delta', 'Scaling operations for the product.', 'Scaling', '2024-09-15'),
(5, 'Project Epsilon', 'Exploring new ideas.', 'Ideation', '2024-02-20'),
(6, 'Project Zeta', 'Development of new features.', 'Development', '2024-07-10'),
(7, 'Project Eta', 'Preparing for market launch.', 'Launch', '2024-04-25'),
(8, 'Project Theta', 'Scaling to new markets.', 'Scaling', '2024-10-05'),
(9, 'Project Iota', 'Initial brainstorming sessions.', 'Ideation', '2024-01-30'),
(10, 'Project Kappa', 'Finalizing development.', 'Development', '2024-08-15');