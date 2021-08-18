resource "google_storage_bucket" "storage-tfstates" {
  name                        = "${var.project_id}-tfstate-dev"
  location                    = "US"
  storage_class               = "STANDARD"
  uniform_bucket_level_access = true

  versioning {
    enabled = true
  }
}

output "bucket_name" {
  value = google_storage_bucket.storage-tfstates.name
}
