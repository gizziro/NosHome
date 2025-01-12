<template>
  <div class="card">
    <div class="card-header">
      <h5 class="mb-0">Basic examples</h5>
    </div>
    <div class="card-body">

    </div>
  </div>
</template>

<script setup>

</script>

<style scoped>
.card {
  display           : flex;
  flex-direction    : column;
  box-sizing        : border-box;
  background-color  : #ffffff;
  margin-bottom   : 1.25rem;
  border  : 1px solid rgb(31, 41, 55, 0.125);
}
.card-header
{
  padding : 15px 20px;
  border-bottom  : 1px solid rgb(31, 41, 55, 0.125);
}
.card-header h5
{
  font-size : 28px;
  font-weight : 600;
  line-height: 28px;
}

.card-body
{
  flex : 1 1 auto;
  padding : 20px;
}

</style>