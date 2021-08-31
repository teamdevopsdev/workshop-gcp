terraform {
  backend "gcs" {}
}

provider "google" {}

variable "project_id" {}
variable "application_name" {}
variable "region" {}
variable "subnetwork_id" {}
variable "network_self_link" {}

/*****************************************
  Activate Services
 *****************************************/

module "api_activation" {
  source     = "../../modules/api_activation"
  project_id = var.project_id
}


/******************************************
	Kubernetes Module
 *****************************************/

module "kubernetes" {
  source            = "../../modules/kubernetes"
  project_id        = var.project_id
  application_name  = var.application_name
  region            = var.region
  network_self_link = var.network_self_link
  subnetwork_id     = var.subnetwork_id
  depends_on        = [module.api_activation]
}

/******************************************
	Cloud SQL Module
 *****************************************/

# module "cloud_sql" {
#   source     = "../../modules/sql_database"
#   region     = var.region
#   depends_on = [module.api_activation]
# }
