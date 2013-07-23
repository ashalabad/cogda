<mt:hasTenant>
    <sec:ifNotLoggedIn>
        <ul class="nav pull-right">
            <li class="">
                <g:link controller="userRegistration" action="index"><i class="icon-user"></i> Register</g:link>
            </li>
        </ul>
    </sec:ifNotLoggedIn>
</mt:hasTenant>
