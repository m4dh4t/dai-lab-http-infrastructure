/*!
* Start Bootstrap - Coming Soon v6.0.7 (https://startbootstrap.com/theme/coming-soon)
* Copyright 2013-2023 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-coming-soon/blob/master/LICENSE)
*/

// Add an event listener to the button
document.getElementById('fetchDataBtn').addEventListener('click', fetchData);

// Function to handle the Fetch API request
function fetchData() {
    // API endpoint URL
    const apiUrl = 'http://localhost/api/';

    // Make a Fetch API request
    fetch(apiUrl)
        .then(response => response.json())
        .then(data => {
            // Display the fetched result in the resultContainer
            const resultContainer = document.getElementById('resultContainer');

            // Clear previous results
            resultContainer.innerHTML = '';

            // Prepare an alert element in case of error
            const alert = document.createElement('p');

            // Check if the data is an array
            if (Array.isArray(data)) {
                if (data.length === 0) {
                    // Display a message if the array is empty
                    alert.textContent = 'No TODO found';
                    alert.className = 'alert alert-dark';
                    resultContainer.appendChild(alert);
                    return;
                }

                // Create a list and append bullet points
                const list = document.createElement('ul');
                list.className = 'list-group list-group-flush';
                data.forEach(item => {
                    const listItem = document.createElement('li');
                    listItem.className = 'list-group-item';
                    listItem.textContent = item;
                    list.appendChild(listItem);
                });

                // Append the list to the resultContainer
                resultContainer.appendChild(list);
            } else {
                // Display an error message if the data is not an array
                alert.textContent = 'Received invalid data';
                alert.className = 'alert alert-danger';
                resultContainer.appendChild(alert);
            }
        })
        .catch(error => {
            // Handle errors
            console.error('Error fetching data:', error);
        });
}
