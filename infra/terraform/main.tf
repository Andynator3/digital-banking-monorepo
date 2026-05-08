# 1. Création d'un Réseau Privé Virtuel (VPC) sécurisé
# On utilise le module officiel AWS pour respecter les meilleures pratiques (Best Practices)
module "vpc" {
  source  = "terraform-aws-modules/vpc/aws"
  version = "5.1.2"

  name = "digital-banking-vpc"
  cidr = "10.0.0.0/16"

  # On utilise deux zones de disponibilité à Paris pour la tolérance aux pannes
  azs             = ["${var.aws_region}a", "${var.aws_region}b"]
  private_subnets = ["10.0.1.0/24", "10.0.2.0/24"] # Là où tourneront nos conteneurs (cachés d'internet)
  public_subnets  = ["10.0.101.0/24", "10.0.102.0/24"] # Pour les répartiteurs de charge (Load Balancers)

  enable_nat_gateway = true
  single_nat_gateway = true # Un seul NAT pour économiser de l'argent sur ce projet

  tags = {
    # Tag obligatoire pour qu'EKS puisse utiliser ce réseau
    "kubernetes.io/cluster/digital-banking-eks" = "shared"
  }
}

# 2. Création du Cluster Kubernetes Managé (EKS)
module "eks" {
  source  = "terraform-aws-modules/eks/aws"
  version = "19.21.0"

  cluster_name    = "digital-banking-eks"
  cluster_version = "1.28" # Version stable de Kubernetes

  vpc_id                         = module.vpc.vpc_id
  subnet_ids                     = module.vpc.private_subnets
  cluster_endpoint_public_access = true # Permet à GitHub Actions de s'y connecter

  # Création des machines "Ouvrières" (Worker Nodes)
  eks_managed_node_groups = {
    app_nodes = {
      min_size     = 1
      max_size     = 3
      desired_size = 2 # On demande 2 machines par défaut

      instance_types = ["t3.medium"] # t3.medium = 2 vCPU, 4GB RAM (parfait pour Spring Boot + Angular)
    }
  }
}
