resource "aws_lambda_function" "example" {
  function_name = "example-lambda"
  handler       = "com.globalbooks.orders.lambda.OrdersLambdaHandler::handleRequest"
  runtime       = "java17"
  filename      = "lambda.zip"
  source_code_hash = filebase64sha256("lambda.zip")
  role          = aws_iam_role.lambda_exec.arn
}

resource "aws_iam_role" "lambda_exec" {
  name = "lambda_exec_role"
  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Action = "sts:AssumeRole"
      Effect = "Allow"
      Principal = {
        Service = "lambda.amazonaws.com"
      }
    }]
  })
}
