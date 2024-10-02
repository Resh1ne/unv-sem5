# pip install pycryptodome
from dataclasses import dataclass
from Crypto import Random
from Crypto.Math.Numbers import Integer
from Crypto.Math.Primality import generate_probable_prime
import os


@dataclass
class RSAKey:
    n: Integer
    e: Integer
    d: Integer


# base ^ exp % mod
def mod_exp(base: Integer, exp: Integer, mod: Integer) -> Integer:
    result = Integer(1)
    while exp > Integer(0):
        if exp % Integer(2) == Integer(1):
            result = (result * base) % mod
        base = (base * base) % mod
        exp //= Integer(2)
    return result


def encrypt(message: str, key: RSAKey) -> Integer:
    message_int = Integer(int.from_bytes(message.encode(), byteorder='big'))
    return mod_exp(message_int, key.e, key.n)
# message ^ e % n(open)


def decrypt(ciphertext: Integer, key: RSAKey) -> str:
    decrypted_message_int = mod_exp(ciphertext, key.d, key.n)
    message_bytes = decrypted_message_int.to_bytes((int(decrypted_message_int).bit_length() + 7) // 8, byteorder='big')
    return message_bytes.decode()
# message ^ d % n(closed)



def generate_rsa(bits, randfunc=None, e=65537):
    if bits < 1024:
        raise ValueError("RSA modulus length must be >= 1024")
    if e % 2 == 0 or e < 3:
        raise ValueError("RSA public exponent must be a positive, odd integer larger than 2.")

    if randfunc is None:
        randfunc = Random.get_random_bytes

    d = n = Integer(1)
    e = Integer(e)

    while n.size_in_bits() != bits and d < (1 << (bits // 2)):
        size_q = bits // 2
        size_p = bits - size_q

        min_p = min_q = (Integer(1) << (2 * size_q - 1)).sqrt()
        if size_q != size_p:
            min_p = (Integer(1) << (2 * size_p - 1)).sqrt()

        def filter_p(candidate):
            return candidate > min_p and (candidate - 1).gcd(e) == 1

        p = generate_probable_prime(exact_bits=size_p, randfunc=randfunc, prime_filter=filter_p)

        min_distance = Integer(1) << (bits // 2 - 100)

        def filter_q(candidate):
            return candidate > min_q and (candidate - 1).gcd(e) == 1 and abs(candidate - p) > min_distance

        q = generate_probable_prime(exact_bits=size_q, randfunc=randfunc, prime_filter=filter_q)

        n = p * q
        lcm = (p - 1).lcm(q - 1)
        d = e.inverse(lcm)

    return RSAKey(n=n, e=e, d=d)


# Сохранение ключей в файлы
def save_keys(rsa_key: RSAKey, pub_file: str, priv_file: str):
    with open(pub_file, 'w') as pub_f:
        pub_f.write(f"{rsa_key.n},{rsa_key.e}")
    with open(priv_file, 'w') as priv_f:
        priv_f.write(f"{rsa_key.n},{rsa_key.d}")


# Загрузка ключей из файлов
def load_keys(pub_file: str, priv_file: str) -> RSAKey:
    with open(pub_file, 'r') as pub_f:
        n, e = map(Integer, pub_f.read().split(','))
    with open(priv_file, 'r') as priv_f:
        _, d = map(Integer, priv_f.read().split(','))
    return RSAKey(n=n, e=e, d=d)


# Сохранение и загрузка сообщения в файл
def save_message(message: str, file_path: str):
    with open(file_path, 'w') as msg_f:
        msg_f.write(message)


def load_message(file_path: str) -> str:
    with open(file_path, 'r') as msg_f:
        return msg_f.read()


# Сохранение и загрузка зашифрованного сообщения
def save_encrypted_message(encrypted_message: Integer, file_path: str):
    with open(file_path, 'w') as enc_f:
        enc_f.write(str(encrypted_message))


def load_encrypted_message(file_path: str) -> Integer:
    with open(file_path, 'r') as enc_f:
        return Integer(int(enc_f.read()))


def rsa_test():
    rsa_key = generate_rsa(1024)
    # (e, n) - open
    # (d, n) - closed
    save_keys(rsa_key, "public_key.txt", "private_key.txt")

    test_messages = [f"Сообщение {i}" for i in range(1, 11)]

    for i, message in enumerate(test_messages):
        save_message(message, f"message_{i}.txt")

        loaded_message = load_message(f"message_{i}.txt")
        encrypted_message = encrypt(loaded_message, rsa_key)
        save_encrypted_message(encrypted_message, f"encrypted_message_{i}.txt")

        loaded_encrypted_message = load_encrypted_message(f"encrypted_message_{i}.txt")
        decrypted_message = decrypt(loaded_encrypted_message, rsa_key)

        print(f"Test {i+1}")
        print(f"Original: {loaded_message}")
        print(f"Decrypted: {decrypted_message}")
        print(f"Test passed: {loaded_message == decrypted_message}\n")


# Запуск тестирования
rsa_test()
