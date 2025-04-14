
(function (document, $) {
    "use strict";

    $(document).on("dialog-ready", function () {
        var multifield = $('coral-multifield[data-granite-coral-multifield-name="./cards"]');
		console.log("=======>");
        if (multifield.length) {
            // Function to attach change listener to title fields.
            function attachTitleChangeListener(field) {
                field.on("change", function () {
                    var titleValue = $(this).val();
                    console.log("Title changed:", titleValue);
                    // Add your custom logic here.
                    // For example, update the description field, or make an api call.
                });
            }

            // Attach listener to existing title fields.
            multifield.find('input[name$="./title"]').each(function () {
                attachTitleChangeListener($(this));
            });

            // Attach listener to newly added title fields.
            multifield.on("coral-collection:add", function (event) {
                var newTitleField = $(event.detail.item).find('input[name$="./title"]');
                attachTitleChangeListener(newTitleField);
            });
        }
    });
})(document, Granite.$);