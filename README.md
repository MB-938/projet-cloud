# k8s-lambda-fullstack-elk

> **FR** — Projet full-stack cloud-native : AWS Lambda (Python), Spring Boot + Angular déployés sur Kubernetes (Minikube), pipeline CI/CD Jenkins, et stack ELK pour la centralisation des logs.
>
> **EN** — Cloud-native full-stack project: AWS Lambda (Python), Spring Boot + Angular deployed on Kubernetes (Minikube), Jenkins CI/CD pipeline, and ELK stack for log centralization.

---

## Stack

![Kubernetes](https://img.shields.io/badge/Kubernetes-Minikube-326CE5?logo=kubernetes)
![AWS Lambda](https://img.shields.io/badge/AWS-Lambda-orange?logo=amazonaws)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-Java-green?logo=springboot)
![Angular](https://img.shields.io/badge/Angular-frontend-red?logo=angular)
![Jenkins](https://img.shields.io/badge/Jenkins-CI%2FCD-red?logo=jenkins)
![Elasticsearch](https://img.shields.io/badge/ELK-Elasticsearch%20%2B%20Kibana-005571?logo=elasticsearch)
![Docker](https://img.shields.io/badge/Docker-Hub-blue?logo=docker)

---

## FR — Architecture

Le projet est divisé en 3 parties :

### Partie 1 — Infrastructure Serverless (AWS Lambda)
- Fonction Lambda Python déployée sur AWS
- Déclenchée via un événement (ex : requête HTTP via API Gateway)
- Démo de l'architecture serverless en complément du cluster Kubernetes

### Partie 2 — Applications sur Kubernetes
- **Spring Boot** : API REST Java containerisée, déployée dans Minikube avec un `Deployment` + `Service`
- **Angular** : Frontend containerisé (nginx), déployé dans Minikube avec un `Deployment` + `Service`
- **Jenkins** : Pipeline CI/CD qui build les images Docker, les pousse sur Docker Hub, et met à jour les déploiements Kubernetes

### Partie 3 — Stack ELK (centralisation des logs)
- **Elasticsearch** : Stockage et indexation des logs
- **Kibana** : Interface de visualisation des logs
- **Filebeat** : Agent de collecte des logs des pods Kubernetes, envoyés vers Elasticsearch

**Concepts démontrés :**
- Déploiement multi-applicatif sur Kubernetes (namespaces, Deployments, Services)
- Pipeline Jenkins → Docker Hub → `kubectl set image` (rolling update)
- Observabilité : collecte des logs applicatifs avec Filebeat DaemonSet

## EN — Architecture

The project is divided into 3 parts:

### Part 1 — Serverless Infrastructure (AWS Lambda)
- Python Lambda function deployed on AWS
- Triggered via an event (e.g., HTTP request via API Gateway)
- Demonstrates serverless architecture alongside the Kubernetes cluster

### Part 2 — Applications on Kubernetes
- **Spring Boot**: Java REST API containerized, deployed in Minikube with a `Deployment` + `Service`
- **Angular**: Containerized frontend (nginx), deployed in Minikube with a `Deployment` + `Service`
- **Jenkins**: CI/CD pipeline that builds Docker images, pushes to Docker Hub, and updates Kubernetes deployments

### Part 3 — ELK Stack (log centralization)
- **Elasticsearch**: Log storage and indexing
- **Kibana**: Log visualization UI
- **Filebeat**: Agent collecting Kubernetes pod logs, forwarding to Elasticsearch

**Concepts demonstrated:**
- Multi-application deployment on Kubernetes (namespaces, Deployments, Services)
- Jenkins pipeline → Docker Hub → `kubectl set image` (rolling update)
- Observability: application log collection with Filebeat DaemonSet

---

## FR — Prérequis

- Docker Desktop
- Minikube + kubectl
- AWS CLI (pour la partie Lambda)
- Compte Docker Hub
- Jenkins (local ou sur une VM)

## EN — Prerequisites

- Docker Desktop
- Minikube + kubectl
- AWS CLI (for the Lambda part)
- Docker Hub account
- Jenkins (local or on a VM)

---

## FR — Démarrage

```bash
# Démarrer Minikube
minikube start

# Partie 2 : Déployer les apps
kubectl apply -f partie2-apps/hello_world/k8s/
kubectl apply -f partie2-apps/hello-angular/k8s/

# Partie 3 : Stack ELK
kubectl apply -f partie3-elk/00-namespace.yaml
kubectl apply -f partie3-elk/01-elasticsearch.yaml
kubectl apply -f partie3-elk/02-kibana.yaml
kubectl apply -f partie3-elk/03-filebeat.yaml
```

## EN — Getting Started

```bash
# Start Minikube
minikube start

# Part 2: Deploy apps
kubectl apply -f partie2-apps/hello_world/k8s/
kubectl apply -f partie2-apps/hello-angular/k8s/

# Part 3: ELK Stack
kubectl apply -f partie3-elk/00-namespace.yaml
kubectl apply -f partie3-elk/01-elasticsearch.yaml
kubectl apply -f partie3-elk/02-kibana.yaml
kubectl apply -f partie3-elk/03-filebeat.yaml
```

---

## Project Structure

```
.
├── Jenkinsfile                         # CI/CD pipeline (build + push + deploy)
├── partie1-infra/
│   └── lambda/
│       └── lambda_function.py         # AWS Lambda function (Python)
├── partie2-apps/
│   ├── hello_world/                   # Spring Boot REST API
│   │   ├── src/
│   │   ├── Dockerfile
│   │   ├── pom.xml
│   │   └── k8s/deployment.yaml
│   └── hello-angular/                 # Angular frontend
│       ├── src/
│       ├── Dockerfile
│       └── k8s/deployment.yaml
└── partie3-elk/
    ├── 00-namespace.yaml
    ├── 01-elasticsearch.yaml
    ├── 02-kibana.yaml
    └── 03-filebeat.yaml               # Filebeat DaemonSet
```
