resource "aws_db_instance" "example" {
  allocated_storage    = 20
  db_name              = "mydb"
  engine               = "postgres"
  engine_version       = "14.5"
  instance_class       = "db.t3.micro"
  username             = "foo"
  password             = "foobar"
  skip_final_snapshot  = true
}
