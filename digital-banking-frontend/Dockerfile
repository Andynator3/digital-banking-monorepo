# Étape 1 : Construction de l'application Angular (avec la bonne version de Node)
FROM node:16.20.2-alpine AS builder
WORKDIR /app

# Optimisation du cache Docker pour les dépendances
COPY package*.json ./
RUN npm install

# Copie du reste du code et build
COPY . .
RUN npm run build --configuration=production

# Étape 2 : Serveur Web Nginx
FROM nginx:alpine

# On remplace la configuration par défaut par la nôtre (pour le routage)
COPY nginx.conf /etc/nginx/conf.d/default.conf

# CORRECTION ICI : On utilise le nom de dossier exact lu dans angular.json
COPY --from=builder /app/dist/digital-banking-web /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
