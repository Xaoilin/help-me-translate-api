# help-me-translate-api

## Deployment Instructions
### API build on ubuntu

sudo apt update
sudo apt install vim
sudo apt install git-all -y
sudo apt install maven -y
cd /home
git clone https://github.com/Xaoilin/help-me-translate-api.git

vi ~/.bashrc (put the below command in bashrc)
export GOOGLE_APPLICATION_CREDENTIALS="/home/api-key/help-me-translate-api-key-prod.json"

nohup mvn spring-boot:run &
