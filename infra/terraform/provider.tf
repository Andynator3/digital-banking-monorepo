# Ce fichier indique à Terraform que nous voulons communiquer avec les API d'AWS
terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
  # On exige une version récente de Terraform
  required_version = ">= 1.3.0"
}

provider "aws" {
  region = var.aws_region

  # Ces tags seront appliqués automatiquement à TOUTES les ressources créées sur AWS
  # Très apprécié par les entreprises pour savoir "qui paie quoi" sur la facture Cloud
  default_tags {
    tags = {
      Project     = "DigitalBanking"
      Environment = "Production"
      ManagedBy   = "Terraform"
    }
  }
}

