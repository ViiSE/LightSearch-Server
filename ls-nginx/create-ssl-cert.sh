echo "Create self-signed key and certificate..."

mkdir key-selfsigned

echo "+-------------------------+"
echo "|OpenSSL|RSA 4096|365 days|"
echo "+-------------------------+"

sudo openssl req -x509 -nodes -days 365 -newkey rsa:4096 -keyout key-selfsigned/selfsigned.key -out key-selfsigned/selfsigned.crt

echo "Key and certificate is created!"

echo "Create Diffie-Hellman group..."
sudo openssl dhparam -out key-selfsigned/dhparam.pem 4096
echo "Done!"

echo "Create read/write privilige..."
sudo chmod ugo+rw key-selfsigned/dhparam.pem
sudo chmod ugo+rw key-selfsigned/selfsigned.crt
sudo chmod ugo+rw key-selfsigned/selfsigned.key

echo "Key and certificate saved in key/ folder"
echo "Don't forget: key and certificate are valid for 365 days."
