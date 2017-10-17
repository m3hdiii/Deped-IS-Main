<!-- CHANGE PASSWORD MODAL -->
<div class="modal" id="change-pass-modal" tabindex="-1" role="dialog" aria-labelledby="Modal-Label" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title" id="Modal-Label">Change Password</h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label for="old-password" class="form-control-label">Old Password:</label>
                        <input type="text" class="form-control" id="old-password">
                    </div>
                    <div class="form-group">
                        <label for="new-password" class="form-control-label">New Password:</label>
                        <input type="text" class="form-control" id="new-password">
                    </div>
                    <div class="form-group">
                        <label for="confirm-password" class="form-control-label">Confirm Password:</label>
                        <input type="text" class="form-control" id="confirm-password">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save Changes</button>
            </div>
        </div>
    </div>
</div>
