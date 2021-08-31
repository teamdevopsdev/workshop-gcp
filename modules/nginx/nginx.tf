resource "helm_release" "nginx-ingress" {
  chart     = "stable/nginx-ingress"
  name      = "nginx-ingress"
  namespace = "kube-system"
  timeout   = "1800"
#   depends_on = [
#     "helm_release.prometheus-operator"
#   ]
  values = [
    "${file("nginx-ingress.values.yaml")}"
  ]
}