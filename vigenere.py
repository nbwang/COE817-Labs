
# Encryption
from typing import Text


def vigenere_encrypt(plain_text, key):
    cipher_text = []
    x = 0
    for i in range(len(plain_text)): #loops through each letter in the plain text
        if ord(plain_text[i]) == 32:#to check for and skip spaces
            cipher_text += chr(32)
            continue
        j = ((ord(plain_text[i]) + ord(key[x])) % 26) + 65 # the calculations for encryption and the 65 for the unicode value for 'A'
        x += 1
        cipher_text += chr(j)
    return cipher_text
	
#decryption 

def vigenere_decrypt(cipher_text, key):
    plain_text = []
    x = 0
    for i in range(len(cipher_text)):#loops through each letter in the cipher text
        if ord(cipher_text[i]) == 32:#to check for and skip spaces
            plain_text += chr(32)
            continue
        j = ord(cipher_text[i]) - ord(key[x])# the calculations for encryption and the 65 for the unicode value for 'A'
        if j < 0:#to check for if the calculations are negative
            j += 26
        j = (j % 26) + 65
        x += 1
        plain_text += chr(j)
    return plain_text
	
while True:
    print('Please Enter Plain Text :"TO BE OR NOT TO BE THAT IS THE QUESTION"')
    plain_text = input()
    print('Please Enter Key :"RELATIONS"')
    key = input()
    if len(key) > len(plain_text):#check for keys longer than the plain text
        print("Error Key is Larger than plain Text")
        continue
    elif len(key) < len(plain_text):# check for if key is shorter and if so copy it until it's the same length
        for i in range(len(plain_text) - len(key)):
            key += key[i % len(key)]
    cipher_text = vigenere_encrypt(plain_text, key)
    strtxt = "".join(cipher_text)#put the array into a string
    print(strtxt)

    plain_text = vigenere_decrypt(cipher_text, key)
    strtxt = "".join(plain_text)#put the array into a string
    print(strtxt)
