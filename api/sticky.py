from requests import Session


def main():
    # Create session
    s = Session()

    # Create new TODO item
    for i in range(10):
        r = s.post('http://localhost/api/todos', data={f"TODO {i}"})
        print(r.status_cod, r.text)



if __name__ == '__main__':
    # Create session
    main()
