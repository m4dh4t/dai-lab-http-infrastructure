import requests


def main():
    print("--- Sticky session ---")
    # Create session
    s = requests.Session()

    # Create new items
    for i in range(10):
        r = s.post('http://localhost/api/', data=f'Item {i}')
        print(r.text)

    # Get all items
    r = s.get('http://localhost/api/')
    print(r.text)

    print("\n--- No sticky session ---")
    # Create new items
    for i in range(10):
        r = requests.post('http://localhost/api/', data=f'Item {i}')
        print(r.text)

    # Get all items
    r = requests.get('http://localhost/api/')
    print(r.text)
    r = requests.get('http://localhost/api/')
    print(r.text)
    r = requests.get('http://localhost/api/')
    print(r.text)
    r = requests.get('http://localhost/api/')
    print(r.text)


if __name__ == '__main__':
    # Create session
    main()
