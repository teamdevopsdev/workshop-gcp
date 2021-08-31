terraform {
  required_providers {
    google = {
      source = "hashicorp/google"
    }
  }
}

variable "project_id" {}

module "enables-google-apis" {
  source  = "terraform-google-modules/project-factory/google//modules/project_services"
  version = "6.0.0"

  project_id = var.project_id

  activate_apis = [
    "iam.googleapis.com",
    "cloudresourcemanager.googleapis.com",
    "compute.googleapis.com",
    "containerregistry.googleapis.com",
    "container.googleapis.com",
    "storage-component.googleapis.com",
    "logging.googleapis.com",
    "monitoring.googleapis.com",
    "sqladmin.googleapis.com",
  ]
}