"""Script to test sticky sessions and show the difference when not used."""
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

    # Cleanup
    r = s.delete('http://localhost/api/')
    print(r.text)

    print("\n--- No sticky session ---")
    # Create new items
    for i in range(10):
        r = requests.post('http://localhost/api/', data=f'Item {i}')
        print(r.text)

    # Get all items
    for _ in range(4):
        r = requests.get('http://localhost/api/')
        print(r.text)


if __name__ == '__main__':
    # Create session
    main()
