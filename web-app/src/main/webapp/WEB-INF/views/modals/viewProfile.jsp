<div class="modal" id="view-user-modal" tabindex="-1" role="dialog" aria-labelledby="Modal-Label" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <!-- modal content -->
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-center" id="Modal-Label">User Profiles</h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="row modal-body profile-body">
                <div class="col-md-12 col-sm-6">
                    <div class="prof-image-container">
                        <img src="../../assets/images/avtar/user.png" class="img-circle prof-image" alt="...">
                    </div>

                    <div class="panel panel-default clearfix rounded icon-bar">
                        <div class="panel-container">
                            <div class="profile-option-container text-right">
                                <img src ="../../assets/images/svg/ic_more_horiz_black_24px.svg"/>
                            </div>
                            <section class="user-information">
                                <div class="text-center">
                                    <h4 class="user-name">${userInfo.firstName} ${userInfo.middleName} ${userInfo.lastName}</h4>
                                    <a class="email-address text-center col-md-12" href="#"><u>${userInfo.emailAddress}</u></a>
                                </div>
                                <div class="container user-information-container">
                                    <div id="prof-birthday">
                                        <label>Birthday:</label>
                                        <p>${userInfo.birthDate}</p>
                                    </div>
                                    <hr class="dotted">

                                    <div id="prof-gender">
                                        <label>Gender:</label>
                                        <p>${userInfo.gender}</p>
                                    </div>
                                    <hr class="dotted">

                                    <div id="prof-phon1">
                                        <label>Phone Number 1:</label>
                                        <p>${userInfo.phoneNo1}</p>
                                    </div>
                                    <hr class="dotted">

                                    <div id="prof-phon2">
                                        <label>Phone Number 2:</label>
                                        <p>${userInfo.phoneNo2}</p>
                                    </div>
                                    <hr class="dotted">

                                    <div id="prof-Address">
                                        <label>address:</label>
                                        <p>${userInfo.address}</p>
                                    </div>
                                    <hr class="dotted">
                                </div>
                            </section>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>