$(document).ready(function() {

  $('#employeeForm').submit(function(e) {
    e.preventDefault();
    var lastName = $('#lastName').val();
    var freePasses = $('#freePasses').val();
    var email = $('#email').val();
    var postalCode = $('#postalCode').val();

//    $(".error").remove();

    if (lastName.length < 1) {
      $('#first_name').after('<span class="error">This field is required</span>');
    }
    if (freePasses.length < 1) {
      $('#last_name').after('<span class="error">This field is required</span>');
    }
    if (email.length < 1) {
      $('#email').after('<span class="error">This field is required</span>');
    } else {
      var regEx = /^[A-Z0-9][A-Z0-9._%+-]{0,63}@(?:[A-Z0-9-]{1,63}\.){1,125}[A-Z]{2,63}$/;
      var validEmail = regEx.test(email);
      if (!validEmail) {
        $('#email').after('<span class="error">Enter a valid email</span>');
      }
    }
    if (postalCode.length < 5) {
      $('#password').after('<span class="error">postalCode must be at least 5 characters long</span>');
    }
  });

});