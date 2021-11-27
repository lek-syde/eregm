function emailSignup() {
    event.preventDefault();
    return {
        // Our "data" -- the email field, messages, and form state
        email: null,
        error: false,
        success: false,
        working: false,

        // When the form is submitted
        submit() {
            // Specify that the form is working (for the loading state)
            this.working = true

            // Using the Fetch API, make a JSON POST request to ConvertKit
            fetch(this.$refs.signupForm.action, {
                method: 'POST',
                headers: {
                    Accept: 'application/json',
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    api_key: 'YOUR_API_KEY',
                    email: this.email,
                }),
            })
            // The response of fetch is a stream object, so two .then()
            // functions are needed here.
            // See here: https://developers.google.com/web/updates/2015/03/introduction-to-fetch?hl=en
                .then(response => response.json())
        .then(json => {
                // If there's an error, display the message
                if (json.error) {
                this.error = json.message
                this.success = false
            } else {
                // No errors, so we'll display our success message or
                // a "you've already subscribed" message if that's the case.
                this.error = false
                if (json.subscription.state == 'active') {
                    this.success = 'Looks like you are already subscribed, thank you!'
                } else {
                    this.success =
                        'Success! Please check your email to confirm your subscription.'
                }
            }
        })
        .finally(() => {
                // Hide the loading state
                this.working = false
        })
        },
        // This allows the analytics for the form to work as if it
        // were a normal form embed from ConvertKit.
        // It is called via x-init in the Alpine component
        trackFormVisit() {
            fetch('https://app.convertkit.com/forms/1570815/visit', {
                method: 'POST',
                headers: {
                    Accept: 'application/json',
                    'Content-Type': 'application/json',
                },
            })
        },
    }
}