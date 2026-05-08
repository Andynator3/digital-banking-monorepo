# Ce fichier contient les variables pour rendre notre code réutilisable
variable "aws_region" {
  description = "La région AWS où déployer l'infrastructure"
  type        = string
  default     = "eu-west-3" # eu-west-3 correspond à Paris.
}
