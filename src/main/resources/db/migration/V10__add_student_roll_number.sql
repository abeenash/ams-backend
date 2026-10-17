ALTER TABLE student ADD COLUMN roll_number INT;

UPDATE student s
SET roll_number = numbered.rn
FROM(
  SELECT id, ROW_NUMBER() OVER (PARTITION BY section_id ORDER BY id) AS rn FROM student
) numbered
WHERE s.id = numbered.id;

ALTER TABLE student ALTER COLUMN roll_number SET NOT NULL;
ALTER TABLE student ADD CONSTRAINT uq_student_section_roll UNIQUE (section_id, roll_number);
ALTER TABLE student ADD CONSTRAINT ck_student_roll_positive CHECK (roll_number > 0);