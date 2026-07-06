# ── Étape 1 : build ───────────────────────────────────────────────────
FROM node:20-alpine AS builder
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npx ng build --configuration=production

# ── Étape 2 : serveur web léger ───────────────────────────────────────
FROM nginx:alpine
COPY --from=builder /app/dist/hello-angular/browser /usr/share/nginx/html
EXPOSE 80