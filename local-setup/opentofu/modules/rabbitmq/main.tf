resource "aws_mq_broker" "example" {
  broker_name        = "example-broker"
  engine_type        = "RabbitMQ"
  engine_version     = "3.9.13"
  host_instance_type = "mq.t3.micro"
  security_groups    = [aws_security_group.example.id]
  user {
    username = "guest"
    password = "guest"
  }
}

resource "aws_security_group" "example" {
  name_prefix = "example-broker-sg"
  ingress {
    from_port   = 5671
    to_port     = 5671
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
}
