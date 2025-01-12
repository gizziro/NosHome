
<template>
  <div class="w-full text-wrap">
    <p>{{ loginUser }}</p>
  </div>
</template>

<script setup>
import { storeToRefs } from "pinia";
import {useAuthStore} from "@/stores/authStore.js";
const authStore = useAuthStore();

const { loginUser } = storeToRefs(authStore);


</script>

<style scoped>

</style>
